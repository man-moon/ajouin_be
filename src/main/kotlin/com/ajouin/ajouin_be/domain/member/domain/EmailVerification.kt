package com.ajouin.ajouin_be.domain.member.domain

import com.ajouin.ajouin_be.global.error.exception.BusinessException
import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.*

@Entity
class EmailVerification (
    @Embedded
    @AttributeOverride(
        name = "value",
        column = Column(name = "email", nullable = false, unique = false)
    )
    val email: Email,
    val code: String,
    var isVerified: Boolean = false,

//    @CreationTimestamp
    @Column(name = "created_at",  updatable = false)
    val createdAt: LocalDateTime? = LocalDateTime.now(),

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,
) {
    fun isExpired(): Boolean {
        return createdAt?.let { it.plusMinutes(3) < LocalDateTime.now() }
            ?: throw BusinessException()
    }
}