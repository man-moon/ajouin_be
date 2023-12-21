package com.ajouin.ajouin_be.domain.post.repository

import com.ajouin.ajouin_be.domain.post.domain.Post
import com.ajouin.ajouin_be.domain.post.domain.PostComment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface PostCommentRepository : JpaRepository<PostComment, Long> {
    fun findById(id: Long?) : PostComment?
    fun findAllByPost(post: Post) : MutableList<PostComment>
}