package com.ajouin.ajouin_be.domain.wiki.dto.request

import jakarta.validation.constraints.NotBlank

data class DocumentUpdateRequest (
    @NotBlank
    val title: String,
    @NotBlank
    val content: String,
)