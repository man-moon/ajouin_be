package com.ajouin.ajouin_be.domain.member.controller

import com.ajouin.ajouin_be.domain.member.domain.AuthUser
import com.ajouin.ajouin_be.domain.member.dto.response.MemberResponse
import com.ajouin.ajouin_be.domain.member.service.MemberServiceImpl
import lombok.extern.slf4j.Slf4j
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@Slf4j
class MemberController(
    private val memberServiceImpl: MemberServiceImpl,
) {
    private val logger = org.slf4j.LoggerFactory.getLogger(this::class.java)
    @GetMapping("/member")
    fun getMemberInfo(): MemberResponse {
        val userId = (SecurityContextHolder.getContext().authentication.principal as AuthUser).id!!
        logger.info(userId.toString())
        val findMember = memberServiceImpl.getMemberInfo(userId)
        return MemberResponse.fromMember(findMember)
    }
}