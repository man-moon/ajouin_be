package com.ajouin.ajouin_be.domain.news.controller

import com.ajouin.ajouin_be.domain.news.dto.request.NoticeRequest
import com.ajouin.ajouin_be.domain.news.dto.response.NoticeResponse
import com.ajouin.ajouin_be.domain.news.service.NoticeService
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*

@RestController
class NoticeController(
    private val noticeService: NoticeService,
) {

    private val logger = LoggerFactory.getLogger(NoticeController::class.java)

    @PostMapping("/notices")
    fun getNotices(@RequestBody noticeRequest: NoticeRequest): NoticeResponse {
        logger.info(noticeRequest.toString())
        val result = noticeService.getNotice(noticeRequest.notices)
        return NoticeResponse(result)
    }
}