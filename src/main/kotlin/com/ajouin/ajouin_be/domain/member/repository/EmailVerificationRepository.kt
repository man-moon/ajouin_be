package com.ajouin.ajouin_be.domain.member.repository

import com.ajouin.ajouin_be.domain.member.domain.EmailVerification
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface EmailVerificationRepository: JpaRepository<EmailVerification, Long> {
    fun findById(id: UUID): EmailVerification?
}