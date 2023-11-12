package com.ajouin.ajouin_be.domain.wiki.dto.request

import jakarta.validation.constraints.NotBlank

data class DocumentViewRequest (
    @NotBlank
    val title: String,
    val version: Long? = null
)