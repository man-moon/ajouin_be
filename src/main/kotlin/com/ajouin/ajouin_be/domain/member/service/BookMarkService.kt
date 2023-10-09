package com.ajouin.ajouin_be.domain.member.service

import com.ajouin.ajouin_be.domain.member.exception.MemberNotFoundException
import com.ajouin.ajouin_be.domain.member.repository.MemberRepository
import com.ajouin.ajouin_be.domain.news.domain.BookMark
import com.ajouin.ajouin_be.domain.news.domain.SchoolNotice
import com.ajouin.ajouin_be.domain.news.dto.response.TogglingBookMarkResponse
import com.ajouin.ajouin_be.domain.news.exception.NoticeNotFoundException
import com.ajouin.ajouin_be.domain.news.repository.BookMarkRepository
import com.ajouin.ajouin_be.domain.news.repository.SchoolNoticeRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class BookMarkService (
    private val memberRepository: MemberRepository,
    private val schoolNoticeRepository: SchoolNoticeRepository,
    private val bookMarkRepository: BookMarkRepository,
) {
    @Transactional
    fun toggleBookMark(memberId: UUID, noticeId: Long): TogglingBookMarkResponse {
        val member = memberRepository.findById(memberId) ?: throw MemberNotFoundException()
        val schoolNotice = schoolNoticeRepository.findByIdOrNull(noticeId) ?: throw NoticeNotFoundException()

        val bookMark = bookMarkRepository.findByMemberAndSchoolNotice(member, schoolNotice)

        return if(bookMark == null) {
            bookMarkRepository.save(BookMark(member, schoolNotice))
            TogglingBookMarkResponse(noticeId, true)
        } else {
            bookMarkRepository.delete(bookMark)
            TogglingBookMarkResponse(noticeId, false)
        }
    }

    @Transactional(readOnly = true)
    fun getBookMark(memberId: UUID): List<SchoolNotice> {
        val member = memberRepository.findById(memberId) ?: throw MemberNotFoundException()
        val bookMarks = bookMarkRepository.findAllByMember(member)
        return bookMarks.map { it.schoolNotice }
    }

}