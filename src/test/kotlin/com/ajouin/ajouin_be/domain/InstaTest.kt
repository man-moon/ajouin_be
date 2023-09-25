package com.ajouin.ajouin_be.domain

import com.github.instagram4j.instagram4j.IGClient
import com.github.instagram4j.instagram4j.actions.feed.FeedItems
import com.github.instagram4j.instagram4j.requests.IGRequest
import com.github.instagram4j.instagram4j.requests.feed.FeedUserRequest
import com.github.instagram4j.instagram4j.responses.feed.FeedUserResponse
import okhttp3.OkHttpClient
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class InstaTest {

    @Test
    fun `인스타그램 로그인`() {
        val client = IGClient.builder()
            .username("_moomani")
            .password("tnsmdrnrdj0258!")
            .login()

        val userSearchResponse = client.actions().users().findByUsername("ajou_council").get()

        val userId = userSearchResponse?.user?.pk

        val feedRequest = FeedUserRequest(userId!!)
        val feedResponse = client.sendRequest(feedRequest).get()

        feedResponse.items.take(10).forEach {
            println("id: ${it.pk}")
            println("caption: ${it.caption?.text}")
            println("likeCount: ${it.like_count}")
//            println("tags: ${it.usertags}")
//            println("timestamp: ${it.device_timestamp}")
//            println("mediaType: ${it.media_type}")
            println("commentCount: ${it.comment_count}")
//            println("takenAt: ${it.taken_at}")
//            println("extraProperties: ${it.extraProperties}")
        }

    }
}