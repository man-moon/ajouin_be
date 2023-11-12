package com.ajouin.ajouin_be.domain.wiki.dto.request

import jakarta.validation.constraints.NotBlank

data class CategoryCreationRequest (
    @NotBlank
    val name: String,
    @NotBlank
    val description: String,
)