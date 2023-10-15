package com.ajouin.ajouin_be.domain.news.controller

import com.ajouin.ajouin_be.domain.member.domain.AuthUser
import com.ajouin.ajouin_be.domain.member.exception.MemberNotFoundException
import com.ajouin.ajouin_be.domain.member.service.BookMarkService
import com.ajouin.ajouin_be.domain.news.dto.request.NoticeRequest
import com.ajouin.ajouin_be.domain.news.dto.response.InstagramNoticeResponse
import com.ajouin.ajouin_be.domain.news.dto.response.NoticeResponse
import com.ajouin.ajouin_be.domain.news.service.NoticeService
import org.apache.coyote.Response
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

@RestController
class NoticeController(
    private val noticeService: NoticeService,
    private val bookMarkService: BookMarkService,
) {

    private val logger = LoggerFactory.getLogger(NoticeController::class.java)

    @PostMapping("/notices")
    fun getNotices(authentication: Authentication?, @RequestBody noticeRequest: NoticeRequest): NoticeResponse {
        val notices = noticeService.getNotice(noticeRequest.notices)
        if(authentication == null) {
            return NoticeResponse(notices)
        }

        val memberId = (authentication.principal as AuthUser).id ?: throw MemberNotFoundException()
        val bookMarks = bookMarkService.getBookMark(memberId)
        return NoticeResponse(notices, bookMarks)
    }

    @GetMapping("/notices")
    fun getSpecificNotices(@RequestParam("type") type: String, @RequestParam("offset") offset: Long): NoticeResponse {
        val notices = noticeService.getNotice(type, offset)
        return NoticeResponse(notices)
    }

    @PostMapping("/views")
    fun postViews(@RequestBody noticeId: Long): ResponseEntity<Any> {
        noticeService.postViews(noticeId)
        return ResponseEntity.ok().build()
    }

    @PostMapping("/council_notices")
    fun getInstagramNotices(@RequestBody noticeRequest: NoticeRequest): InstagramNoticeResponse {
        val result = noticeService.getInstagramNotice(noticeRequest.notices)
        return InstagramNoticeResponse(result)
    }

    @GetMapping("/testapi")
    fun testApi(): String {
        return "test"
    }
}