package com.ajouin.ajouin_be.domain.news.service.crawler

import com.ajouin.ajouin_be.domain.news.domain.LastIdPerType
import com.ajouin.ajouin_be.domain.news.domain.SchoolNotice
import com.ajouin.ajouin_be.domain.news.domain.Type
import com.ajouin.ajouin_be.domain.news.repository.LastIdPerTypeRepository
import com.ajouin.ajouin_be.domain.news.repository.SchoolNoticeRepository
import com.slack.api.Slack
import org.jsoup.Jsoup
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SchoolNoticeCrawler (
    private val schoolNoticeRepository: SchoolNoticeRepository,
    private val lastIdPerTypeRepository: LastIdPerTypeRepository,
    private val strategies: List<NoticeCrawlerStrategy>,
) {

    private val logger = LoggerFactory.getLogger(SchoolNoticeCrawler::class.java)

    @Value("\${slack.webhook.url}")
    lateinit var webHookUrl: String
    @Scheduled(fixedRate = 600000)  // 10분마다
    @Transactional
    fun crawl() {
        for (strategy in strategies) {
            crawlByStrategy(strategy)
        }
        logger.info("공지사항 데이터 수집 완료")

        val slackClient = Slack.getInstance()
        slackClient.send(webHookUrl, "{\"text\" : \"공지사항 데이터 수집 완료\"}")
    }

    fun crawlByStrategy(strategy: NoticeCrawlerStrategy) {
        val url = strategy.url
        val doc = Jsoup.connect(url).get()
        val rows = doc.select(strategy.selector)

        val notices = mutableListOf<SchoolNotice>()

        var lastIdByType = lastIdPerTypeRepository.findByType(strategy.type)
        if(lastIdByType == null) {
            lastIdByType = lastIdPerTypeRepository.save(LastIdPerType(type = strategy.type, lastId = 0))
        }

        var lastId = lastIdByType.lastId

        for (row in rows) {
            strategy.parseNotice(row, lastId)?.let {
                //소프트웨어학과인 경우만 따로 처리
                if(it.type == Type.소프트웨어학과0 || it.type == Type.소프트웨어학과1) {
                    it.link = "http://software.ajou.ac.kr" + it.link
                } else {
                    it.link = url + it.link
                }
                notices.add(it)
            }
        }

        lastId = notices.maxOfOrNull { it.fetchId } ?: return
        lastIdByType.lastId = lastId

        schoolNoticeRepository.saveAll(notices)
    }

}