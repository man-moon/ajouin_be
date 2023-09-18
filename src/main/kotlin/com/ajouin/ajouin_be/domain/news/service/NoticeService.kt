package com.ajouin.ajouin_be.domain.news.service

import com.ajouin.ajouin_be.domain.news.domain.SchoolNotice
import com.ajouin.ajouin_be.domain.news.domain.Type
import com.ajouin.ajouin_be.domain.news.repository.SchoolNoticeQuerydslRepository
import com.ajouin.ajouin_be.domain.news.repository.SchoolNoticeRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class NoticeService (
    private val schoolNoticeRepository: SchoolNoticeRepository,
    private val schoolNoticeQuerydslRepository: SchoolNoticeQuerydslRepository,
) {
    private val logger = org.slf4j.LoggerFactory.getLogger(NoticeService::class.java)
    @Transactional
    fun getNotice(notices: List<String>): List<SchoolNotice> {
        val result = mutableListOf<SchoolNotice>()

        for (notice in notices) {
            val types: List<Type> = findEnumValuesContaining(notice)
            schoolNoticeQuerydslRepository.findByTypeContaining(types).let {
                result.addAll(it)
            }
        }

        result.sortByDescending { it.fetchId }

        return result
    }

    fun findEnumValuesContaining(substring: String): List<Type> {
        return Type.values().filter { it.name.contains(substring) }
    }
}