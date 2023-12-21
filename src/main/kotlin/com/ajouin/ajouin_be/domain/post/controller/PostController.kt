package com.ajouin.ajouin_be.domain.post.controller

import com.ajouin.ajouin_be.domain.member.domain.AuthUser
import com.ajouin.ajouin_be.domain.post.dto.request.PostCommentCreationRequest
import com.ajouin.ajouin_be.domain.post.dto.request.PostCreationRequest
import com.ajouin.ajouin_be.domain.post.dto.request.PostUpdateRequest
import com.ajouin.ajouin_be.domain.post.dto.response.PostCommentResponse
import com.ajouin.ajouin_be.domain.post.dto.response.PostResponse
import com.ajouin.ajouin_be.domain.post.service.PostCommentServiceImpl
import com.ajouin.ajouin_be.domain.post.service.PostServiceImpl
import com.ajouin.ajouin_be.domain.post.service.PreferenceServiceImpl
import com.ajouin.ajouin_be.global.config.security.exception.InvalidTokenException
import org.springframework.data.domain.Page
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("/board")
class PostController(
    val postService: PostServiceImpl,
    val preferenceService: PreferenceServiceImpl,
    val postCommentService: PostCommentServiceImpl,
) {
    private val logger = org.slf4j.LoggerFactory.getLogger(this::class.java)
    @PostMapping("/post")
    fun createPost(
        @RequestBody postCreationRequest: PostCreationRequest,
        authentication: Authentication
    ): PostResponse {
        val memberId = getMemberId(authentication)
        return postService.create(postCreationRequest, memberId)
    }

    @PostMapping("/post/{postId}/preference")
    fun preferPost(
        @PathVariable postId: Long,
        authentication: Authentication
    ): Int {
        val memberId = getMemberId(authentication)
        return preferenceService.postPreferenceManage(postId, memberId)
    }

    @PostMapping("/comment/{commentId}/preference")
    fun preferComment(
        @PathVariable commentId: Long,
        authentication: Authentication
    ): Int {
        val memberId = getMemberId(authentication)
        return preferenceService.commentPreferenceManage(commentId, memberId)
    }

    @PostMapping("/post/{postId}/comment")
    fun createPostComment(
        @PathVariable postId: Long,
        @RequestBody postCommentCreationRequest: PostCommentCreationRequest,
        authentication: Authentication
    ): PostCommentResponse {
        val memberId = getMemberId(authentication)
        return postCommentService.createPostComment(postId, memberId, postCommentCreationRequest)
    }

    @PostMapping("/post/comment-child")
    fun createPostCommentChild(
        @RequestParam("postId") postId: Long,
        @RequestParam("commentId") commentId: Long,
        @RequestBody postCommentCreationRequest: PostCommentCreationRequest,
        authentication: Authentication
    ): PostCommentResponse {
        val memberId = getMemberId(authentication)
        postCommentCreationRequest.parent = commentId
        return postCommentService.createPostComment(postId, memberId, postCommentCreationRequest)
    }

    @GetMapping
    fun getListPost(@RequestParam("page") page: Int): Page<PostResponse> {
        return postService.readList(page)
    }

    @GetMapping("/post")
    fun getPostsByTag(
        @RequestParam("tag") tag: String,
        @RequestParam("page") page: Int
    ): Page<PostResponse> {
        return postService.getListByTag(tag, page)
    }

    @GetMapping("/post/{postId}")
    fun getPost(@PathVariable postId: Long, authentication: Authentication? = null): PostResponse {
        if(authentication != null) {
            val memberId = getMemberId(authentication)
            return postService.readSingle(postId, memberId)
        }
        return postService.readSingle(postId)
    }

    @PutMapping("/post/{postId}")
    fun updatePost(
        @PathVariable postId: Long,
        @RequestBody postUpdateRequest: PostUpdateRequest,
        authentication: Authentication
    ): PostResponse {
        val memberId = getMemberId(authentication)
        return postService.update(postId, postUpdateRequest, memberId)
    }

    @DeleteMapping("/post/{postId}")
    fun deletePost(
        @PathVariable postId: Long,
        authentication: Authentication
    ): PostResponse {
        val memberId = getMemberId(authentication)
        return postService.delete(postId, memberId)
    }

    @DeleteMapping("/comment/{commentId}")
    fun deletePostComment(
        @PathVariable commentId: Long,
        authentication: Authentication
    ): PostCommentResponse {
        val memberId = getMemberId(authentication)
        return postCommentService.deletePostComment(commentId, memberId)
    }

    private fun getMemberId(authentication: Authentication): UUID {
        return (authentication.principal as AuthUser).id ?: throw InvalidTokenException()
    }
}