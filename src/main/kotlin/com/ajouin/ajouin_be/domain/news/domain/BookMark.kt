package com.ajouin.ajouin_be.domain.news.domain

import com.ajouin.ajouin_be.domain.member.domain.Member
import com.ajouin.ajouin_be.domain.news.domain.SchoolNotice
import jakarta.persistence.*
import java.util.*

@Entity
class BookMark (

    @ManyToOne(fetch = FetchType.LAZY)
    val member: Member,

    @ManyToOne(fetch = FetchType.LAZY)
    val schoolNotice: SchoolNotice,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
)