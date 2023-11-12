package com.ajouin.ajouin_be.domain.wiki.dto.request

import com.ajouin.ajouin_be.domain.wiki.domain.Category
import jakarta.persistence.FetchType
import jakarta.persistence.ManyToOne
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class DocumentCreationRequest (
    @NotBlank
    val title: String,
    @NotBlank
    val content: String,
    @NotNull
    val categoryName: String,
)