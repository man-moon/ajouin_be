package com.ajouin.ajouin_be.domain.member.service

import com.ajouin.ajouin_be.domain.member.dto.request.SignupRequest
import com.ajouin.ajouin_be.domain.member.domain.Member
import com.ajouin.ajouin_be.domain.member.dto.request.LoginRequest
import com.ajouin.ajouin_be.domain.member.dto.request.PasswordResetRequest
import com.ajouin.ajouin_be.global.config.security.TokenInfo
import java.util.UUID

interface AuthService {
    fun signup(signupRequest: SignupRequest): Member
    fun login(loginRequest: LoginRequest): TokenInfo
    fun verifyNickname(nickname: String): Boolean
    fun resetPassword(passwordResetRequest: PasswordResetRequest): Boolean
    fun withdrawal(memberId: UUID): Boolean
}