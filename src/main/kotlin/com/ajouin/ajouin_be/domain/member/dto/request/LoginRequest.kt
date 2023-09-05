package com.ajouin.ajouin_be.domain.member.dto.request

import com.ajouin.ajouin_be.domain.member.domain.Email
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class LoginRequest(
    @field:Valid
    val email: Email,
    @field:NotBlank @field:Size(min = 8)
    val password: String,
)