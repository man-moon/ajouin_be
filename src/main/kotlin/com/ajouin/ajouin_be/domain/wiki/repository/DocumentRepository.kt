package com.ajouin.ajouin_be.domain.wiki.repository

import com.ajouin.ajouin_be.domain.wiki.domain.Category
import com.ajouin.ajouin_be.domain.wiki.domain.Document
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface DocumentRepository: JpaRepository<Document, Long> {
    fun findByTitle(title: String): Document?

    fun findByTitleAndVersion(title: String, version: Long): Document?

    @Query("SELECT MAX(d.version) FROM Document d WHERE d.title = :title")
    fun findTopVersionByTitle(title: String): Long?

    fun findAllByCategory(category: Category): List<Document>?

    fun findAllByTitle(title: String): List<Document>?

    @Query("SELECT d FROM Document d WHERE d.version = (SELECT MAX(d2.version) FROM Document d2 WHERE d2.title = d.title) ORDER BY d.title ASC")
    fun findAllLatestByTitle(): List<Document>

}