package com.ajouin.ajouin_be.domain.news.domain

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime

@Entity
class InstagramNotice (
    @Column(columnDefinition="TEXT", length = 2048)
    val caption: String,
    val likeCount: Int,
    val commentCount: Int,
    val councilType: String,
    val councilName: String,

    @CreationTimestamp
    val createdAt: LocalDateTime? = null,

    @Id
    val id: Long? = null,
)