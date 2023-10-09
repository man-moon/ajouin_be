package com.ajouin.ajouin_be.domain.member.service

import com.ajouin.ajouin_be.domain.member.domain.EmailVerification
import com.ajouin.ajouin_be.domain.member.dto.request.CodeVerificationRequest
import com.ajouin.ajouin_be.domain.member.dto.request.EmailVerificationRequest
import com.ajouin.ajouin_be.domain.member.dto.request.PasswordResetRequest

interface EmailVerificationService {
    fun sendVerificationEmail(emailVerificationRequest: EmailVerificationRequest): EmailVerification
    fun verifyCode(codeVerificationRequest: CodeVerificationRequest): Boolean
    fun sendVerificationEmailForResetPassword(emailVerificationRequest: EmailVerificationRequest): EmailVerification
}