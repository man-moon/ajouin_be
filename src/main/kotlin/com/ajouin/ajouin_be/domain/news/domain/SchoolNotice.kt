package com.ajouin.ajouin_be.domain.news.domain

import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.Date

@Entity
class SchoolNotice (
    val title: String,
    var link: String,
    val isTopFixed: Boolean,
    val date: Date,
    var views: Long = 0,
    @Enumerated(EnumType.STRING)
    val type: Type,

    var fetchId: Long,

    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
)