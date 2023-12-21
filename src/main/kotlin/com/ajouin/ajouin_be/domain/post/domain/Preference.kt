package com.ajouin.ajouin_be.domain.post.domain

import com.ajouin.ajouin_be.domain.member.domain.Member
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime
import java.util.UUID

@Entity
class Preference(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne
    @JoinColumn(name = "post_id")
    val post: Post? = null,

    @ManyToOne
    @JoinColumn(name = "comment_id")
    val comment: PostComment? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    val member: Member,

    @CreationTimestamp
    @Column(name = "created_at")
    val createdAt: LocalDateTime = LocalDateTime.now(),
)