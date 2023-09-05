package com.ajouin.ajouin_be.domain.member.service

import com.ajouin.ajouin_be.domain.member.domain.EmailVerification
import com.ajouin.ajouin_be.domain.member.dto.request.CodeVerificationRequest
import com.ajouin.ajouin_be.domain.member.dto.request.EmailVerificationRequest

interface EmailVerificationService {
    fun sendVerificationEmail(emailVerificationRequest: EmailVerificationRequest): EmailVerification
    fun verifyCode(codeVerificationRequest: CodeVerificationRequest): Boolean
}