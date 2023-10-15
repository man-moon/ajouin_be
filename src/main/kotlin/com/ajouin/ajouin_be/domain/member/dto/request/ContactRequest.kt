package com.ajouin.ajouin_be.domain.member.dto.request

import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class ContactRequest (
    @field:NotBlank
    val classification: String,

    @field:NotBlank @field:Size(max = 3000)
    val content: String,
)