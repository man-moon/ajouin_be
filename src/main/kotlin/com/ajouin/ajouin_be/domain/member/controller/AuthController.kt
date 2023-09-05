package com.ajouin.ajouin_be.domain.member.controller

import com.ajouin.ajouin_be.domain.member.dto.request.CodeVerificationRequest
import com.ajouin.ajouin_be.domain.member.dto.request.EmailVerificationRequest
import com.ajouin.ajouin_be.domain.member.dto.request.LoginRequest
import com.ajouin.ajouin_be.domain.member.dto.request.SignupRequest
import com.ajouin.ajouin_be.domain.member.dto.response.CodeVerificationResponse
import com.ajouin.ajouin_be.domain.member.dto.response.EmailVerificationResponse
import com.ajouin.ajouin_be.domain.member.dto.response.SignupResponse
import com.ajouin.ajouin_be.domain.member.service.AuthService
import com.ajouin.ajouin_be.domain.member.service.EmailVerificationService
import com.ajouin.ajouin_be.domain.member.domain.EmailVerification
import com.ajouin.ajouin_be.domain.member.domain.Member
import com.ajouin.ajouin_be.global.config.security.TokenInfo
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*
import org.springframework.http.*

@RestController
@RequestMapping("/auth")
class AuthController(
    private val authService: AuthService,
    private val emailVerificationService: EmailVerificationService,
) {
    @PostMapping("/signup")
    fun signup(@Valid @RequestBody signupRequest: SignupRequest): SignupResponse {

        val member: Member = authService.signup(signupRequest)
        return SignupResponse.fromMember(member)
    }

    @PostMapping("/login")
    fun login(@Valid @RequestBody loginRequest: LoginRequest): ResponseEntity<Any> {
        val tokenInfo: TokenInfo = authService.login(loginRequest)
        return ResponseEntity.ok().headers{
                it.set("Authorization", "${tokenInfo.grantType} ${tokenInfo.accessToken}")
            }.build()
    }

    @PostMapping("/nickname")
    fun verifyNickname(
        @RequestBody nickname: String
    ): Boolean {
        return authService.verifyNickname(nickname)
    }

    //이메일 요청 및 재요청처리
    @PostMapping("/email/request")
    fun sendVerificationEmail(
        @Valid @RequestBody emailVerificationRequest: EmailVerificationRequest
    ): EmailVerificationResponse {
        val emailVerification: EmailVerification = emailVerificationService.sendVerificationEmail(emailVerificationRequest)
        return EmailVerificationResponse.fromEmailVerification(emailVerification)
    }

    @PostMapping("/email/verify")
    fun verifyEmail(
        @Valid @RequestBody codeVerificationRequest: CodeVerificationRequest
    ): CodeVerificationResponse {
        return CodeVerificationResponse(
            isVerified = emailVerificationService.verifyCode(codeVerificationRequest)
        )
    }

}