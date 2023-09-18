package com.ajouin.ajouin_be.domain.news.domain

import jakarta.persistence.*
import java.util.Date

@Entity
class SchoolNotice (
    val title: String,
    var link: String,
    val isTopFixed: Boolean,
    val date: Date,

    @Enumerated(EnumType.STRING)
    val type: Type,

    val fetchId: Long,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
)