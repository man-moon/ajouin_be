package com.ajouin.ajouin_be.domain.post.dto.response

import java.time.LocalDateTime
import java.util.*

data class PostCommentResponse (
        var isWrittenByMe: Boolean = false,
        val commentId: Long,
        val content: String,
        val writerNickname: String,
        val preferenceCount: Int,
        val timeAgo: String,
        val childComments: MutableList<PostCommentResponse> = mutableListOf(),
)