package com.ajouin.ajouin_be.domain.member.dto.request

import com.ajouin.ajouin_be.domain.member.domain.Email
import jakarta.validation.Valid


data class EmailVerificationRequest(
    @field:Valid
    val email: Email,
)