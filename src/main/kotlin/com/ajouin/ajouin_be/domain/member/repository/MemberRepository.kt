package com.ajouin.ajouin_be.domain.member.repository

import com.ajouin.ajouin_be.domain.member.domain.Email
import com.ajouin.ajouin_be.domain.member.domain.Member
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface MemberRepository: JpaRepository<Member, Long> {
    fun existsByEmail(email: Email): Boolean
    fun findByEmail(email: Email): Member?
    fun existsByNickname(nickname: String): Boolean
    fun findById(id: UUID): Member?
}