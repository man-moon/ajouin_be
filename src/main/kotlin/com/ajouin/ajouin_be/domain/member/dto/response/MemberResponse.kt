package com.ajouin.ajouin_be.domain.member.dto.response

import com.ajouin.ajouin_be.domain.member.domain.Email
import com.ajouin.ajouin_be.domain.member.domain.Member

data class MemberResponse(
    val nickname: String,
    val email: Email,
    val enrollYear: Int,
) {
    companion object {
        fun fromMember(member: Member): MemberResponse {
            return MemberResponse(
                nickname = member.nickname,
                email = member.email,
                enrollYear = member.enrollYear,
            )
        }
    }

}