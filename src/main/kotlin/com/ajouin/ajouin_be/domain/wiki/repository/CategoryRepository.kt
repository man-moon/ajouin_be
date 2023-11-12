package com.ajouin.ajouin_be.domain.wiki.repository

import com.ajouin.ajouin_be.domain.wiki.domain.Category
import org.springframework.data.jpa.repository.JpaRepository

interface CategoryRepository: JpaRepository<Category, Long> {

    fun findByName(name: String): Category?

}