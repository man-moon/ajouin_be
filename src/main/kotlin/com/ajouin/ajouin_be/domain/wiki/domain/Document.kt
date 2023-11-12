package com.ajouin.ajouin_be.domain.wiki.domain

import com.ajouin.ajouin_be.domain.member.domain.Member
import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import java.time.LocalDateTime

@Entity
class Document (
    @NotBlank
    val title: String,
    @NotBlank
    @Lob
    val content: String,

    @ManyToOne(fetch = FetchType.LAZY)
    val category: Category,

    val createdAt: LocalDateTime = LocalDateTime.now(),
    val version: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    val writer: Member,

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    val id: Long? = null,
)