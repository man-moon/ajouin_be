package com.ajouin.ajouin_be.domain.post.dto.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import java.util.*

class PostCommentCreationRequest (
        var parent: Long? = null,
        @field:NotBlank @field:Size(min = 2, max = 4500)
        val content: String,
        @field:NotNull
        val isAnonymous: Boolean,
)