package com.ajouin.ajouin_be.domain.news.repository

import com.ajouin.ajouin_be.domain.news.domain.QSchoolNotice
import com.ajouin.ajouin_be.domain.news.domain.SchoolNotice
import com.ajouin.ajouin_be.domain.news.domain.Type
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional(readOnly = true)
class SchoolNoticeQuerydslRepository(
    val queryFactory: JPAQueryFactory
) {
    fun findByTypeContaining(types: List<Type>): List<SchoolNotice> {
        val qSchoolNotice = QSchoolNotice.schoolNotice // QSchoolNotice는 QueryDSL이 생성한 Q 클래스

        // 동적 쿼리 생성
        return queryFactory
            .selectFrom(qSchoolNotice)
            .where(typeIn(types))
            .fetch()
    }

    private fun typeIn(types: List<Type>): BooleanExpression? {
        val qSchoolNotice = QSchoolNotice.schoolNotice
        return types.map { qSchoolNotice.type.eq(it) }
            .reduce(BooleanExpression::or)
    }
}