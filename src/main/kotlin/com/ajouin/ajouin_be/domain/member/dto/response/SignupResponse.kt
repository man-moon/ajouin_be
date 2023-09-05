package com.ajouin.ajouin_be.domain.member.dto.response

import com.ajouin.ajouin_be.domain.member.domain.Email
import com.ajouin.ajouin_be.domain.member.domain.Member

data class SignupResponse(
    val nickname: String,
    val email: Email,
) {
    companion object {
        fun fromMember(member: Member): SignupResponse {
            return SignupResponse(
                nickname = member.nickname,
                email = member.email,
            )
        }
    }


}