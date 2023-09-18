package com.ajouin.ajouin_be.domain.news.domain

import jakarta.persistence.*

@Entity
class LastIdPerType (

    var lastId: Long,

    @Enumerated(EnumType.STRING)
    @Column(unique = true)
    val type: Type,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
)