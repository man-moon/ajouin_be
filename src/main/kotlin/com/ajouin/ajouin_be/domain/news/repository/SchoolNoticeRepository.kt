package com.ajouin.ajouin_be.domain.news.repository

import com.ajouin.ajouin_be.domain.news.domain.SchoolNotice
import com.ajouin.ajouin_be.domain.news.domain.Type
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface SchoolNoticeRepository: JpaRepository<SchoolNotice, Long> {
    fun findTopByOrderByIdDesc(): SchoolNotice?

    fun findAllByTypeAndIsTopFixedIsTrue(type: Type): List<SchoolNotice>
    fun findByFetchIdAndType(fetchId: Long, type: Type): SchoolNotice?
}