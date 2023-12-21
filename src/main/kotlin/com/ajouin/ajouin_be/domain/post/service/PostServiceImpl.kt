package com.ajouin.ajouin_be.domain.post.service

import com.ajouin.ajouin_be.domain.member.exception.MemberNotFoundException
import com.ajouin.ajouin_be.domain.member.repository.MemberRepository
import com.ajouin.ajouin_be.domain.model.InputFilter
import com.ajouin.ajouin_be.domain.post.domain.Post
import com.ajouin.ajouin_be.domain.post.domain.PostComment
import com.ajouin.ajouin_be.domain.post.domain.PostTag
import com.ajouin.ajouin_be.domain.post.dto.request.PostUpdateRequest
import com.ajouin.ajouin_be.domain.post.dto.request.PostCreationRequest
import com.ajouin.ajouin_be.domain.post.dto.response.PostCommentResponse
import com.ajouin.ajouin_be.domain.post.dto.response.PostResponse
import com.ajouin.ajouin_be.domain.post.repository.PostCommentRepository
import com.ajouin.ajouin_be.domain.post.repository.PostRepository
import com.ajouin.ajouin_be.domain.post.repository.PostTagRepository
import com.ajouin.ajouin_be.domain.post.exception.PostContentInvalidException
import com.ajouin.ajouin_be.domain.post.exception.PostTagInvalidException
import com.ajouin.ajouin_be.domain.post.exception.PostTitleInvalidException
import com.ajouin.ajouin_be.global.error.exception.EntityNotFoundException
import com.ajouin.ajouin_be.global.error.exception.HandleAccessException
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.transaction.annotation.Transactional
import org.springframework.stereotype.Service
import java.time.Duration
import java.time.LocalDateTime
import java.util.*

@Service
class PostServiceImpl(
    val postRepository: PostRepository,
    val postTagRepository: PostTagRepository,
    val postCommentRepository: PostCommentRepository,
    val memberRepository: MemberRepository,
) {

    private val logger = org.slf4j.LoggerFactory.getLogger(this::class.java)
    @Transactional
    fun create(postCreationRequest: PostCreationRequest, memberId: UUID): PostResponse {

        val postTag = getPostTag(postCreationRequest.tagName)
        val writer = memberRepository.findById(memberId) ?: throw MemberNotFoundException()

        val newPost = Post(
            title = postCreationRequest.title,
            content = postCreationRequest.content,
            tag = postTag,
            writer = writer,
            viewCount = 0,
            isAnonymous = postCreationRequest.isAnonymous,
        )

        val savedPost = postRepository.save(newPost)

        return PostResponse(
            postId = savedPost.id!!,
            title = savedPost.title,
            content = savedPost.content,
            writerNickname = if (savedPost.isAnonymous) "익명" else writer.nickname,
            tagName = savedPost.tag.tagName,
            preferenceCount = savedPost.preferences!!.size,
            viewCount = savedPost.viewCount,
            timeAgo = formatTimeAgo(savedPost.createdAt, savedPost.updatedAt),
        )
    }

    @Transactional
    fun readList(page: Int): Page<PostResponse> {
        val pageable: Pageable = PageRequest.of(page, 10, Sort.by("createdAt").descending())

        return postRepository.findAll(pageable).map {
            PostResponse(
                postId = it.id!!,
                title = it.title,
                content = it.content,
                writerNickname = if (it.isAnonymous) "익명" else it.writer.nickname,
                tagName = it.tag.tagName,
                preferenceCount = it.preferences!!.size,
                viewCount = it.viewCount,
                commentCount = postCommentRepository.findAllByPost(it).size,
                timeAgo = formatTimeAgo(it.createdAt, it.updatedAt),
            )
        }
    }

    @Transactional
    fun readSingle(postId: Long, memberId: UUID? = null): PostResponse {
        val postDetail = postRepository.findByIdOrNull(postId) ?: throw EntityNotFoundException()
        val commentList = postCommentRepository.findAllByPost(postDetail)

        var isWrittenByMe = false
        if(memberId != null && postDetail.writer.id == memberId) {
            isWrittenByMe = true
        }

        postDetail.viewCount += 1

        val sortedComment = if(memberId == null) commentAnonymityAndSort(commentList)
            else commentAnonymityAndSort(commentList, memberId)

        return PostResponse(
            postId = postDetail.id!!,
            title = postDetail.title,
            content = postDetail.content,
            writerNickname = if (postDetail.isAnonymous) "익명" else postDetail.writer.nickname,
            tagName = postDetail.tag.tagName,
            preferenceCount = postDetail.preferences!!.size,
            viewCount = postDetail.viewCount,
            commentList = sortedComment,
            commentCount = commentList.size,
            timeAgo = formatTimeAgo(postDetail.createdAt, postDetail.updatedAt),
            isWrittenByMe = isWrittenByMe,
        )
    }

    @Transactional
    fun update(postId: Long, postUpdateRequest: PostUpdateRequest, memberId: UUID): PostResponse {
        if (InputFilter.isInputNotValid(postUpdateRequest.title)) throw PostTitleInvalidException()
        if (InputFilter.isInputNotValid(postUpdateRequest.content)) throw PostContentInvalidException()

        val savedPost = postRepository.findByIdOrNull(postId) ?: throw EntityNotFoundException()
        if (savedPost.writer.id != memberId) throw HandleAccessException()


        val postTag = getPostTag(postUpdateRequest.tagName)

        savedPost.title = postUpdateRequest.title
        savedPost.content = postUpdateRequest.content
        savedPost.tag = postTag
        savedPost.updatedAt = LocalDateTime.now()

        return PostResponse(
            postId = savedPost.id!!,
            title = savedPost.title,
            content = savedPost.content,
            writerNickname = if (savedPost.isAnonymous) "익명" else savedPost.writer.nickname,
            tagName = savedPost.tag.tagName,
            preferenceCount = savedPost.preferences!!.size,
            viewCount = savedPost.viewCount,
            timeAgo = formatTimeAgo(savedPost.createdAt, savedPost.updatedAt),
        )
    }

    @Transactional
    fun delete(postId: Long, memberId: UUID): PostResponse {

        val postDetail = postRepository.findByIdOrNull(postId) ?: throw EntityNotFoundException()

        if (postDetail.writer.id != memberId) throw HandleAccessException()

        // 댓글 삭제
        val commentList = postCommentRepository.findAllByPost(postDetail)
        commentList.forEach {
            postCommentRepository.delete(it)
        }
        // 게시글 삭제
        postRepository.delete(postDetail)

        return PostResponse(
            postId = postDetail.id!!,
            title = postDetail.title,
            content = postDetail.content,
            writerNickname = if (postDetail.isAnonymous) "익명" else postDetail.writer.nickname,
            tagName = postDetail.tag.tagName,
            preferenceCount = postDetail.preferences!!.size,
            viewCount = postDetail.viewCount,
            timeAgo = formatTimeAgo(postDetail.createdAt, postDetail.updatedAt),
        )
    }

    @Transactional
    fun getListByTag(tag: String, page: Int): Page<PostResponse> {

        val realTag = "#$tag"

        val postTag = postTagRepository.findByTagName(realTag) ?: throw EntityNotFoundException()
        val pageable: Pageable = PageRequest.of(page, 10, Sort.by("createdAt").descending())
        return postRepository.findAllByTag(postTag, pageable).map {
            PostResponse(
                postId = it.id!!,
                title = it.title,
                content = it.content,
                writerNickname = if (it.isAnonymous) "익명" else it.writer.nickname,
                tagName = it.tag.tagName,
                preferenceCount = it.preferences!!.size,
                viewCount = it.viewCount,
                timeAgo = formatTimeAgo(it.createdAt, it.updatedAt),
            )
        }
    }

    fun getPostTag(tagName: String): PostTag {
        return postTagRepository.findByTagName(tagName) ?: throw PostTagInvalidException()
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

    //댓글 정렬
    fun commentAnonymityAndSort(commentList: List<PostComment>, memberId: UUID? = null): List<PostCommentResponse> {
        val sortedComments = commentList.sortedBy { it.createdAt }
        val anonymousCounterMap = mutableMapOf<UUID, Int>()
        val commentResponseList = mutableListOf<PostCommentResponse>()
        var anonymousPlus = 1
        var commentResponse: PostCommentResponse
        // 댓글 익명 처리 및 정렬된 순으로 댓글 리스트 생성
        sortedComments.forEach { comment ->
            //작성자 댓글 처리
            if (comment.writer.id == comment.post.writer.id) {
                commentResponse = PostCommentResponse(
                    commentId = comment.id!!,
                    writerNickname = "글쓴이",
                    content = comment.content,
                    preferenceCount = comment.preferences.size,
                    timeAgo = formatTimeAgo(comment.createdAt),
                )
            } else {
                if (anonymousCounterMap[comment.writer.id!!] == null) {
                    anonymousCounterMap[comment.writer.id] = anonymousPlus++
                }
                val anonymousCounter = anonymousCounterMap[comment.writer.id]
                val writerNickname = "익명${anonymousCounter}"
                commentResponse = PostCommentResponse(
                    commentId = comment.id!!,
                    writerNickname = writerNickname,
                    content = comment.content,
                    preferenceCount = comment.preferences.size,
                    timeAgo = formatTimeAgo(comment.createdAt),
                )
            }
            // 대댓글인 경우 부모 댓글의 childComments에 추가
            val parentComment = commentResponseList.find { it.commentId == comment.parent?.id }
            if (parentComment != null) {
                parentComment.childComments.add(commentResponse)
            } else {
                commentResponseList.add(commentResponse)
            }
            if(memberId != null && memberId == comment.writer.id) {
                commentResponse.isWrittenByMe = true
            }
        }
        return commentResponseList.toList()
    }

}