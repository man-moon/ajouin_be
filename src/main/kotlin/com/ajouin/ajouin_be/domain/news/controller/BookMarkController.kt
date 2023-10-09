package com.ajouin.ajouin_be.domain.news.controller

import com.ajouin.ajouin_be.domain.member.domain.AuthUser
import com.ajouin.ajouin_be.domain.member.exception.MemberNotFoundException
import com.ajouin.ajouin_be.domain.member.service.BookMarkService
import com.ajouin.ajouin_be.domain.news.dto.response.BookMarkResponse
import com.ajouin.ajouin_be.domain.news.dto.response.TogglingBookMarkResponse
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*
import java.security.InvalidParameterException
import java.security.Principal

@RestController
@RequestMapping("/bookmark")
class BookMarkController(
    private val bookMarkService: BookMarkService,
) {

    @PostMapping
    fun toggleBookMark(authentication: Authentication, @RequestBody noticeId: Long): TogglingBookMarkResponse {
        val memberId = getMemberId(authentication)
        return bookMarkService.toggleBookMark(memberId, noticeId)
    }

    @GetMapping
    fun getBookMarks(authentication: Authentication): BookMarkResponse {
        val memberId = getMemberId(authentication)

        return BookMarkResponse(bookMarkService.getBookMark(memberId))
    }

    private fun getMemberId(authentication: Authentication) =
        (authentication.principal as AuthUser).id ?: throw MemberNotFoundException()
}