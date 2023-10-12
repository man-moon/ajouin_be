package com.ajouin.ajouin_be.domain.news.repository

import com.ajouin.ajouin_be.domain.member.domain.Member
import com.ajouin.ajouin_be.domain.news.domain.BookMark
import com.ajouin.ajouin_be.domain.news.domain.SchoolNotice
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BookMarkRepository: JpaRepository<BookMark, Long> {
    fun findAllByMember(member: Member): List<BookMark>
    fun findByMemberAndSchoolNotice(member: Member, schoolNotice: SchoolNotice): BookMark?
    fun deleteAllByMember(member: Member)

}