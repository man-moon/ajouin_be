package com.ajouin.ajouin_be.domain.post.repository

import com.ajouin.ajouin_be.domain.post.domain.PostTag
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface PostTagRepository: JpaRepository<PostTag, Long> {
    fun findByTagName(tagName: String): PostTag?
}