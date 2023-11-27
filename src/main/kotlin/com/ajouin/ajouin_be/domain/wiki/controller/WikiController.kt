package com.ajouin.ajouin_be.domain.wiki.controller

import com.ajouin.ajouin_be.domain.member.domain.AuthUser
import com.ajouin.ajouin_be.domain.member.exception.MemberNotFoundException
import com.ajouin.ajouin_be.domain.wiki.domain.Category
import com.ajouin.ajouin_be.domain.wiki.domain.Document
import com.ajouin.ajouin_be.domain.wiki.dto.request.*
import com.ajouin.ajouin_be.domain.wiki.dto.response.DocumentCreationResponse
import com.ajouin.ajouin_be.domain.wiki.dto.response.DocumentListResponse
import com.ajouin.ajouin_be.domain.wiki.dto.response.DocumentUpdateResponse
import com.ajouin.ajouin_be.domain.wiki.service.WikiService
import org.slf4j.LoggerFactory
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/wiki")
class WikiController(
    private val wikiService: WikiService,
) {
    private val logger = LoggerFactory.getLogger(WikiController::class.java)

    @PostMapping
    fun createDocument(
        authentication: Authentication,
        @RequestBody documentCreationRequest: DocumentCreationRequest
    ): DocumentCreationResponse {
        val memberId = getMemberId(authentication)
        return wikiService.createDocument(
            memberId,
            documentCreationRequest
        )
    }

    @PatchMapping
    fun updateDocument(
        authentication: Authentication,
        @RequestBody documentUpdateRequest: DocumentUpdateRequest
    ): DocumentUpdateResponse {
        val memberId = getMemberId(authentication)
        return wikiService.updateDocument(memberId, documentUpdateRequest)
    }

    //한 문서 조회
    //version이 null이면 최신버전으로 리턴
    @GetMapping("/document")
    fun getDocument(@RequestParam doc: String, @RequestParam ver: Long?): Document {
        return wikiService.getDocument(doc, ver)
    }

    //모든 문서리스트 조회
    @GetMapping
    fun getAllDocuments(): DocumentListResponse {
        return wikiService.getAllDocuments()
    }

    //문서 모든 버전 조회
    @GetMapping("/versions/{title}")
    fun getAllVersions(@PathVariable title: String): DocumentListResponse {
        return wikiService.getAllVersionsByDocument(title)
    }

    //카테고리 별 문서리스트 조회
    @GetMapping("/category/{name}")
    fun getDocumentsByCategory(@PathVariable name: String): DocumentListResponse {
        return wikiService.getDocumentsByCategory(name)
    }

    @PostMapping("/category")
    fun createCategory(
        authentication: Authentication,
        @RequestBody categoryCreationRequest: CategoryCreationRequest
    ): Category {

        val memberId = getMemberId(authentication)
        return wikiService.createCategory(memberId, categoryCreationRequest)
    }

    @GetMapping("categories")
    fun getAllCategories(): List<Category> {
        return wikiService.getAllCategories()
    }

    private fun getMemberId(authentication: Authentication) =
        (authentication.principal as AuthUser).id ?: throw MemberNotFoundException()
}