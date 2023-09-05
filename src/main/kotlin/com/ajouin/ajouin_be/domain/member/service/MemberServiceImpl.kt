package com.ajouin.ajouin_be.domain.member.service

import com.ajouin.ajouin_be.domain.member.domain.Member
import com.ajouin.ajouin_be.domain.member.exception.MemberNotFoundException
import com.ajouin.ajouin_be.domain.member.repository.MemberRepository
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class MemberServiceImpl(
    private val memberRepository: MemberRepository,
) {
    fun getMemberInfo(userId: UUID): Member {
        return memberRepository.findById(userId) ?: throw MemberNotFoundException()
    }


}