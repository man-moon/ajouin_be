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
        val qSchoolNotice = QSchoolNotice.schoolNotice

        // 동적 쿼리 생성
        return queryFactory
            .selectFrom(qSchoolNotice)
            .where(typeIn(types))
            .fetch()
    }

    //상단 고정 공지는 항상 모두 가져오고, 상단 고정 공지가 아닌 공지사항을 fetchId가 큰 순으로 10개씩 가져오기(페이징 처리)
    fun findNoticesByPaging(
        offset: Long,
        limit: Long,
        types: List<Type>,
        includeTopFixed: Boolean
    ): List<SchoolNotice> {
        val qSchoolNotice = QSchoolNotice.schoolNotice

        // 상단 고정 공지를 모두 가져옵니다.
        val topFixedNotices = if(includeTopFixed) {
            queryFactory
                .selectFrom(qSchoolNotice)
                .where(typeIn(types))
                .where(qSchoolNotice.isTopFixed.eq(true))
                .orderBy(qSchoolNotice.fetchId.desc())
                .fetch()
        } else {
            emptyList()
        }

        // 상단 고정이 아닌 공지를 페이징 처리하여 가져옵니다.
        val generalNotices = queryFactory
            .selectFrom(qSchoolNotice)
            .where(typeIn(types))
            .where(qSchoolNotice.isTopFixed.eq(false))
            .orderBy(qSchoolNotice.fetchId.desc())
            .offset(offset)
            .limit(limit)
            .fetch()

        return topFixedNotices + generalNotices
    }

    fun findNoticesByPagingForSoftwareDep(
        offset: Long,
        limit: Long,
        types: List<Type>,
        includeTopFixed: Boolean
    ): List<SchoolNotice> {
        val qSchoolNotice = QSchoolNotice.schoolNotice

        // 상단 고정 공지를 모두 가져옵니다.
        val topFixedNotices = if(includeTopFixed) {
            queryFactory
                .selectFrom(qSchoolNotice)
                .where(typeIn(types))
                .where(qSchoolNotice.isTopFixed.eq(true))
                .orderBy(qSchoolNotice.fetchId.desc())
                .fetch()
        } else {
            emptyList()
        }

        // 상단 고정이 아닌 공지를 페이징 처리하여 가져옵니다.
        val generalNotices = queryFactory
            .selectFrom(qSchoolNotice)
            .where(typeIn(types))
            .where(qSchoolNotice.isTopFixed.eq(false))
            .orderBy(qSchoolNotice.date.desc())
            .offset(offset)
            .limit(limit)
            .fetch()

        return topFixedNotices + generalNotices
    }

    private fun typeIn(types: List<Type>): BooleanExpression? {
        val qSchoolNotice = QSchoolNotice.schoolNotice
        return types.map { qSchoolNotice.type.eq(it) }
            .reduce(BooleanExpression::or)
    }
}