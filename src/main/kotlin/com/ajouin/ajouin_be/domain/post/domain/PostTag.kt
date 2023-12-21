package com.ajouin.ajouin_be.domain.post.domain

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime
import java.util.UUID

@Entity
class PostTag(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "tag_name", nullable = false)
    val tagName: String,

    @CreationTimestamp
    @Column(name = "created_at")
    val createdAt: LocalDateTime? = null,

    ) {
    companion object {
        fun fixture(
            tagName: String = "testTag",
        ): PostTag {
            return PostTag(
                tagName = tagName,
            )
        }
    }
}