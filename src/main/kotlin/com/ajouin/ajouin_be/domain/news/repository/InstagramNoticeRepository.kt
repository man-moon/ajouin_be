package com.ajouin.ajouin_be.domain.news.repository

import com.ajouin.ajouin_be.domain.news.domain.InstagramNotice
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface InstagramNoticeRepository: JpaRepository<InstagramNotice, Long> {
    fun findByCouncilType(type: String): List<InstagramNotice>
}