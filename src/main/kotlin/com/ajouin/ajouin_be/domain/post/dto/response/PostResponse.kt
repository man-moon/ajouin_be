package com.ajouin.ajouin_be.domain.post.dto.response

data class PostResponse(
    val postId: Long,
    val title: String,
    val content: String,
    val writerNickname: String,
    val tagName: String,
    val preferenceCount: Int = 0,
    val commentCount: Int = 0,
    val viewCount: Int = 0,
    val commentList: List<PostCommentResponse> = mutableListOf(),
    val timeAgo: String,
    val isWrittenByMe: Boolean = false,
)