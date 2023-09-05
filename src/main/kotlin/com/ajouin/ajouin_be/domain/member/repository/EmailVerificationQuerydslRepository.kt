package com.ajouin.ajouin_be.domain.member.repository

import com.ajouin.ajouin_be.domain.member.domain.Email
import com.ajouin.ajouin_be.domain.member.domain.QEmailVerification.emailVerification
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Component

@Component
class EmailVerificationQuerydslRepository(
    private val queryFactory: JPAQueryFactory,
) {
    fun notExists(email: Email): Boolean {
        return queryFactory.select(emailVerification)
            .from(emailVerification)
            .where(
                emailVerification.email.eq(email),
                emailVerification.isVerified.eq(true)
            )
            .fetchFirst() == null
    }
}