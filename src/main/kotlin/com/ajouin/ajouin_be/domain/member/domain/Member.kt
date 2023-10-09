package com.ajouin.ajouin_be.domain.member.domain

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime
import java.util.*

@Entity
class Member(
    @Column(nullable = false)
    val nickname: String,

    @Embedded
    @AttributeOverride(
        name = "value",
        column = Column(name = "email", nullable = false, unique = true)
    )
    val email: Email,
    var password: String,
    val enrollYear: Int,

    @CreationTimestamp
    @Column(name = "created_at",  updatable = false)
    val createdAt: LocalDateTime? = null,

    @Enumerated(EnumType.STRING)
    val role: Role,

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    ) {
    companion object {
        fun fixture(
            nickname: String = "TestMember",
            email: Email = Email("moonman0429@ajou.ac.kr"),
            password: String = "password123",
            enrollYear: Int = 2023,
            createdAt: LocalDateTime = LocalDateTime.now(),
            role: Role = Role.MEMBER,
            id: UUID? = null,
        ): Member {
            return Member(nickname, email, password, enrollYear, createdAt, role, id)
        }
    }
}