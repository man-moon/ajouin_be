package com.ajouin.ajouin_be.domain.member.service

import com.ajouin.ajouin_be.domain.member.domain.AuthUser
import com.ajouin.ajouin_be.domain.member.domain.Email
import com.ajouin.ajouin_be.domain.member.domain.Member
import com.ajouin.ajouin_be.domain.member.repository.MemberRepository
import com.ajouin.ajouin_be.global.error.exception.ErrorCode
import lombok.extern.slf4j.Slf4j
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Slf4j
@Service
class UserDetailServiceImpl(
    private val memberRepository: MemberRepository,
): UserDetailsService {

    private val logger = org.slf4j.LoggerFactory.getLogger(this::class.java)
    override fun loadUserByUsername(email: String): UserDetails {
        return memberRepository.findByEmail(Email(email))
            ?. let { createUserDetails(it) }
            ?: throw UsernameNotFoundException(ErrorCode.LOGIN_INPUT_INVALID.message)
    }

    private fun createUserDetails(member: Member): UserDetails {
        return AuthUser(
            id = member.id,
            email = member.email.value,
            password = member.password,
            //한 유저는 단일 권한을 가진다. 만약 여러 권한을 가질 경우 map으로 변환하여 처리
            authorities = listOf(SimpleGrantedAuthority("ROLE_${member.role.name}"))
        )
    }
}