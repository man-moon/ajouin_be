package com.ajouin.ajouin_be.domain.news.repository

import com.ajouin.ajouin_be.domain.news.domain.LastIdPerType
import com.ajouin.ajouin_be.domain.news.domain.Type
import org.springframework.data.jpa.repository.JpaRepository

interface LastIdPerTypeRepository: JpaRepository<LastIdPerType, Long> {
    fun findByType(type: Type): LastIdPerType?

}