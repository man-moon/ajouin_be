package com.ajouin.ajouin_be.domain.news.service

import com.ajouin.ajouin_be.domain.news.domain.InstagramNotice
import com.ajouin.ajouin_be.domain.news.domain.SchoolNotice
import com.ajouin.ajouin_be.domain.news.domain.Type
import com.ajouin.ajouin_be.domain.news.exception.NoticeNotFoundException
import com.ajouin.ajouin_be.domain.news.repository.InstagramNoticeRepository
import com.ajouin.ajouin_be.domain.news.repository.SchoolNoticeQuerydslRepository
import com.ajouin.ajouin_be.domain.news.repository.SchoolNoticeRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class NoticeService (
    private val schoolNoticeRepository: SchoolNoticeRepository,
    private val schoolNoticeQuerydslRepository: SchoolNoticeQuerydslRepository,
    private val instagramNoticeRepository: InstagramNoticeRepository,
) {
    private val logger = org.slf4j.LoggerFactory.getLogger(NoticeService::class.java)
    @Transactional
    fun getNotice(notices: List<String>): List<SchoolNotice> {
        val result = mutableListOf<SchoolNotice>()

        //상단 고정 공지는 항상 모두 가져오고, 상단 고정 공지가 아닌 공지사항을 fetchId가 큰 순으로 10개씩 가져오기(페이징 처리)

        for (notice in notices) {
            val types: List<Type> = findEnumValuesContaining(notice)
            schoolNoticeQuerydslRepository.findNoticesByPaging(0, 10, types, true).let {
                result.addAll(it)
            }
        }
        result.sortByDescending { it.fetchId }

        return result
    }

    @Transactional
    fun getNotice(type: String, offset: Long): List<SchoolNotice> {
        val types: List<Type> = findEnumValuesContaining(type)
        return if(types.contains(Type.소프트웨어학과0)) {
            schoolNoticeQuerydslRepository.findNoticesByPagingForSoftwareDep(offset, 10, types, false)
        } else {
            schoolNoticeQuerydslRepository.findNoticesByPaging(offset, 10, types, false)
        }
    }

    @Transactional
    fun getInstagramNotice(notices: List<String>): List<InstagramNotice> {
        val result = mutableListOf<InstagramNotice>()

        for (notice in notices) {
            instagramNoticeRepository.findByCouncilType(notice).let {
                result.addAll(it)
            }
        }

        result.sortByDescending { it.id }

        return result

    }

    @Transactional
    fun postViews(noticeId: Long) {
        val notice = schoolNoticeRepository.findByIdOrNull(noticeId) ?: throw NoticeNotFoundException()
        notice.views += 1
    }

    fun findEnumValuesContaining(substring: String): List<Type> {
        return Type.values().filter { it.name.contains(substring) }
    }
}