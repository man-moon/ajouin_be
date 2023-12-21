package com.ajouin.ajouin_be.domain.post.repository

import com.ajouin.ajouin_be.domain.post.domain.Post
import com.ajouin.ajouin_be.domain.post.domain.PostTag
import com.ajouin.ajouin_be.domain.post.dto.request.PostUpdateRequest
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface PostRepository : JpaRepository<Post, Long>, PagingAndSortingRepository<Post, Long>{
    fun save(postUpdateRequest: PostUpdateRequest): Long
    override fun findAll(pageable: Pageable): Page<Post>
    fun findAllByTag(tag: PostTag, pageable: Pageable): Page<Post>
}