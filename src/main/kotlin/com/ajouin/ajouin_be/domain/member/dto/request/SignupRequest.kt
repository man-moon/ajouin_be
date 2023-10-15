package com.ajouin.ajouin_be.domain.member.dto.request

import com.ajouin.ajouin_be.domain.member.domain.Email
import com.ajouin.ajouin_be.domain.member.domain.Member
import com.ajouin.ajouin_be.domain.member.domain.Role
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class SignupRequest(
    @field:Valid
    val email: Email,
    @field:NotBlank @field:Size(min = 8, max = 20)
    val password: String,
) {
    fun toMember(): Member {
        return Member(
            nickname = "익명",
            email = this.email,
            password = this.password,
            enrollYear = 2023,
            role = Role.MEMBER,
        )
    }
}
