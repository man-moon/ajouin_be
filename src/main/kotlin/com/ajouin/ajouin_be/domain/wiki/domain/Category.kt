package com.ajouin.ajouin_be.domain.wiki.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.validation.constraints.NotBlank
import java.time.LocalDateTime

@Entity
class Category (
    @NotBlank
    @Column(unique = true)
    val name: String,
    @NotBlank
    val description: String,

    val createdAt: LocalDateTime = LocalDateTime.now(),

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    val id: Long? = null,
)