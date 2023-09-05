package com.ajouin.ajouin_be.domain.member.dto.response

import com.ajouin.ajouin_be.domain.member.domain.Email
import com.ajouin.ajouin_be.domain.member.domain.EmailVerification
import java.util.*

data class EmailVerificationResponse(
    val email: Email,
    val id: UUID?,
) {
    companion object {
        fun fromEmailVerification(emailVerification: EmailVerification): EmailVerificationResponse {
            return EmailVerificationResponse(
                email = emailVerification.email,
                id = emailVerification.id,
            )
        }
    }
}