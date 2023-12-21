package com.ajouin.ajouin_be.domain.post.service

import com.ajouin.ajouin_be.domain.post.domain.PostComment
import com.ajouin.ajouin_be.domain.post.dto.request.PostCommentCreationRequest
import com.ajouin.ajouin_be.domain.post.dto.response.PostCommentResponse
import com.ajouin.ajouin_be.domain.post.exception.PostContentInvalidException
import com.ajouin.ajouin_be.domain.post.repository.PostCommentRepository
import com.ajouin.ajouin_be.domain.post.repository.PostRepository
import com.ajouin.ajouin_be.domain.member.repository.MemberRepository
import com.ajouin.ajouin_be.domain.model.InputFilter
import com.ajouin.ajouin_be.global.error.exception.EntityNotFoundException
import com.ajouin.ajouin_be.global.error.exception.HandleAccessException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Duration
import java.time.LocalDateTime
import java.util.UUID

@Service
class PostCommentServiceImpl(
    val postCommentRepository: PostCommentRepository,
    val postRepository: PostRepository,
    val memberRepository: MemberRepository,
) {
    private val logger = org.slf4j.LoggerFactory.getLogger(this::class.java)
    @Transactional
    fun createPostComment(
        postId: Long,
        memberId: UUID,
        postCommentCreationRequest: PostCommentCreationRequest
    ): PostCommentResponse {
        val post = postRepository.findByIdOrNull(postId) ?: throw EntityNotFoundException()
        val writer = memberRepository.findById(memberId) ?: throw EntityNotFoundException()

        val parentPostComment: PostComment? =
            if(postCommentCreationRequest.parent == null)
                null
            else postCommentRepository.findById(postCommentCreationRequest.parent)

        val newPostComment = PostComment(
            post = post,
            writer = writer,
            content = postCommentCreationRequest.content,
            parent = parentPostComment,
            isAnonymous = postCommentCreationRequest.isAnonymous,
        )

        val savedPostComment = postCommentRepository.save(newPostComment)

        //todo: 익명1, 익명2 처리 필요
        return PostCommentResponse(
            commentId = savedPostComment.id!!,
            content = savedPostComment.content,
            writerNickname = if (savedPostComment.isAnonymous) "익명" else writer.nickname,
            timeAgo = formatTimeAgo(savedPostComment.createdAt),
            preferenceCount = savedPostComment.preferences.size,
        )
    }

    @Transactional
    fun deletePostComment(commentId: Long, memberId: UUID): PostCommentResponse {
        val postComment = postCommentRepository.findByIdOrNull(commentId) ?: throw EntityNotFoundException()
        val writer = memberRepository.findById(memberId) ?: throw EntityNotFoundException()

        if (writer != postComment.writer) throw HandleAccessException()

//        postComment.isDeleted = true
        postCommentRepository.delete(postComment)
        return PostCommentResponse(
            commentId = postComment.id!!,
            content = postComment.content,
            writerNickname = "익명",
            timeAgo = formatTimeAgo(postComment.createdAt),
            preferenceCount = postComment.preferences.size,
        )
    }

    fun formatTimeAgo(createdTime: LocalDateTime, updatedTime: LocalDateTime? = null): String {
        val now = LocalDateTime.now()
        val duration = Duration.between(createdTime, now)
        val form = when {
            duration.toMinutes() < 1 -> "방금 전"
            duration.toHours() < 1 -> "${duration.toMinutes()}분 전"
            duration.toHours() < 24 -> "${duration.toHours()}시간 전"
            duration.toDays() < 7 -> "${duration.toDays()}일 전"
            duration.toDays() < 30 -> "${duration.toDays() / 7}주 전"
            duration.toDays() < 365 -> "${duration.toDays() / 30}달 전"
            else -> "${duration.toDays() / 365}년 전"
        }
        return form
    }
}