package com.ajouin.ajouin_be.domain.post.dto.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class PostCreationRequest(
    @field:NotBlank @field:Size(min = 2, max = 60)
    val title: String,
    @field:NotBlank @field:Size(min = 2, max = 4500)
    val content: String,
    @field:NotBlank @field:Size(min = 2, max = 60)
    val tagName: String,
    @field:NotNull
    val isAnonymous: Boolean,
) {
    companion object {
        fun toPostCreation(postCreationRequest: PostCreationRequest): PostCreationRequest {
            return PostCreationRequest(
                title = postCreationRequest.title,
                content = postCreationRequest.content,
                tagName = postCreationRequest.tagName,
                isAnonymous = postCreationRequest.isAnonymous
            )
        }
    }
}