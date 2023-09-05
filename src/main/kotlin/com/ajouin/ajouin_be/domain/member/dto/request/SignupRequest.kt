package com.ajouin.ajouin_be.domain.member.dto.request

import com.ajouin.ajouin_be.domain.member.domain.Email
import com.ajouin.ajouin_be.domain.member.domain.Member
import com.ajouin.ajouin_be.domain.member.domain.Role
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class SignupRequest(
    @field:NotBlank @field:Size(min = 2, max = 10)
    val nickname: String,
    @field:Valid
    val email: Email,
    @field:NotBlank @field:Size(min = 8)
    val password: String,
    val enrollYear: Int,
) {
    fun toMember(): Member {
        return Member(
            nickname = this.nickname,
            email = this.email,
            password = this.password,
            enrollYear = this.enrollYear,
            role = Role.MEMBER,
        )
    }
}
