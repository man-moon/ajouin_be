package com.ajouin.ajouin_be.domain.news.service.crawler

import com.ajouin.ajouin_be.domain.news.domain.InstagramAccounts
import com.ajouin.ajouin_be.domain.news.domain.InstagramNotice
import com.ajouin.ajouin_be.domain.news.repository.InstagramNoticeRepository
import com.ajouin.ajouin_be.global.config.InstagramConfig
import com.github.instagram4j.instagram4j.IGClient
import com.github.instagram4j.instagram4j.IGClient.Builder.LoginHandler
import com.github.instagram4j.instagram4j.requests.feed.FeedUserRequest
import com.github.instagram4j.instagram4j.responses.IGResponse
import com.github.instagram4j.instagram4j.responses.accounts.LoginResponse
import com.github.instagram4j.instagram4j.responses.feed.FeedUserResponse
import com.github.instagram4j.instagram4j.utils.IGChallengeUtils
import com.slack.api.Slack
import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.nio.charset.Charset
import java.util.*
import java.util.concurrent.Callable
import java.util.concurrent.TimeUnit
import kotlin.random.Random
import kotlin.random.nextLong


@Service
class InstagramNoticeCrawler (
    private val instagramConfig: InstagramConfig,
    private val instagramNoticeRepository: InstagramNoticeRepository,
) {

    private val logger = org.slf4j.LoggerFactory.getLogger(InstagramNoticeCrawler::class.java)

    @Value("\${slack.webhook.url}")
    lateinit var webHookUrl: String

//    @Scheduled(fixedRate = 43200000)  // 12시간마다
    fun crawl() {
        try {
            // Map 순서 랜덤으로 배치
            val shuffledAccounts = InstagramAccounts.instagramAccounts.toList().shuffled().toMap()

            val usernames = listOf(instagramConfig.username1, instagramConfig.username2)
            val passwords = listOf(instagramConfig.password1, instagramConfig.password2)

            // 이 Map을 8등분
            val chunkSize = shuffledAccounts.size / 8
            val partitions = shuffledAccounts.toList().chunked(chunkSize).map { it.toMap() }

            // 외부 루프 시작: Map 하나씩 꺼내오기
            for ((index, partition) in partitions.withIndex()) {
                val username = usernames[index % usernames.size]
                val password = passwords[index % passwords.size]

                // IGLoginException 발생시, return
                val client: IGClient = IGClient.builder()
                    .username(username)
                    .password(password)
                    .login()

                // 내부 루프 시작: 각 Instagram 계정에 대해
                for ((key, value) in partition) {
                    val userSearchResponse = client.actions().users().findByUsername(value).get()
                    val userId = userSearchResponse?.user?.pk ?: continue
                    val feedRequest = FeedUserRequest(userId)
                    val feedResponse: FeedUserResponse = client.sendRequest(feedRequest).get()

                    feedResponse.items.take(5).forEach { item ->
                        val id = item.pk
                        val caption = item.caption?.text ?: return@forEach
                        val likeCount = item.like_count
                        val commentCount = item.comment_count

                        val instagramNotice = InstagramNotice(
                            id = id,
                            caption = caption,
                            likeCount = likeCount,
                            councilType = key,
                            councilName = value,
                            commentCount = commentCount,
                        )

                        // DB에 저장
                        instagramNoticeRepository.save(instagramNotice)
                    }

                    // 7초~12초 사이 랜덤 대기
                    val sleepTime = Random.nextLong(7000, 12000)  // 7000 to 11999 milliseconds
                    logger.info("Sleeping for $sleepTime milliseconds")
                    TimeUnit.MILLISECONDS.sleep(sleepTime)
                }
                // 10분~15분 사이 랜덤 대기
                val longSleepTime = Random.nextLong(600000, 900000)  // 600000 to 899999 milliseconds
                logger.info("Sleeping for $longSleepTime milliseconds")
                TimeUnit.MILLISECONDS.sleep(longSleepTime)
            }
        } catch (e: Exception) {
            // 예외 발생 시 Slack 알림 전송 로직
            val slackClient = Slack()
            slackClient.send(webHookUrl, "Instagram Crawler Error: ${e.message}")
            return
        }

    }
}