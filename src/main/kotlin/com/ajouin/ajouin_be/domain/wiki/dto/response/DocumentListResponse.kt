package com.ajouin.ajouin_be.domain.wiki.dto.response

import com.ajouin.ajouin_be.domain.wiki.domain.Category
import java.time.LocalDateTime

data class DocumentListResponse (
    val documents: List<DocumentResponse>
)

data class DocumentResponse (
    val title: String,
    val createdAt: LocalDateTime,
)