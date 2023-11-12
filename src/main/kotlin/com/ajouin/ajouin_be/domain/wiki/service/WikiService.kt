package com.ajouin.ajouin_be.domain.wiki.service

import com.ajouin.ajouin_be.domain.member.exception.MemberNotFoundException
import com.ajouin.ajouin_be.domain.member.repository.MemberRepository
import com.ajouin.ajouin_be.domain.wiki.domain.Category
import com.ajouin.ajouin_be.domain.wiki.domain.Document
import com.ajouin.ajouin_be.domain.wiki.dto.request.*
import com.ajouin.ajouin_be.domain.wiki.dto.response.DocumentCreationResponse
import com.ajouin.ajouin_be.domain.wiki.dto.response.DocumentListResponse
import com.ajouin.ajouin_be.domain.wiki.dto.response.DocumentResponse
import com.ajouin.ajouin_be.domain.wiki.dto.response.DocumentUpdateResponse
import com.ajouin.ajouin_be.domain.wiki.exception.CategoryNotFoundException
import com.ajouin.ajouin_be.domain.wiki.exception.DocumentNotFoundException
import com.ajouin.ajouin_be.domain.wiki.repository.CategoryRepository
import com.ajouin.ajouin_be.domain.wiki.repository.DocumentRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class WikiService(
    private val memberRepository: MemberRepository,
    private val documentRepository: DocumentRepository,
    private val categoryRepository: CategoryRepository
) {
    @Transactional
    fun createDocument(
        memberId: UUID,
        documentCreationRequest: DocumentCreationRequest
    ): DocumentCreationResponse {

        val member = memberRepository.findById(memberId)
            ?: throw MemberNotFoundException()

        val category = categoryRepository.findByName(documentCreationRequest.categoryName)
            ?: throw CategoryNotFoundException()

        val document = Document(
            title = documentCreationRequest.title,
            content = documentCreationRequest.content,
            category = category,
            writer = member
        )
        val savedDocument = documentRepository.save(document)

        return DocumentCreationResponse(
            id = savedDocument.id!!,
            version = savedDocument.version
        )
    }

    @Transactional
    fun updateDocument(
        memberId: UUID,
        documentUpdateRequest: DocumentUpdateRequest
    ): DocumentUpdateResponse {

        val member = memberRepository.findById(memberId)
            ?: throw MemberNotFoundException()


        val latestVersion = documentRepository.findTopVersionByTitle(documentUpdateRequest.title)
            ?: throw DocumentNotFoundException()

        val document = documentRepository.findByTitleAndVersion(documentUpdateRequest.title, latestVersion)
            ?: throw DocumentNotFoundException()

        val newDocument = Document(
            title = document.title,
            content = documentUpdateRequest.content,
            category = document.category,
            writer = member,
            version = document.version + 1,
        )

        val savedDocument = documentRepository.save(newDocument)

        return DocumentUpdateResponse(
            id = savedDocument.id!!,
            version = savedDocument.version
        )
    }

    @Transactional
    fun getDocument(doc: String, ver: Long?): Document {

        var latestVersion: Long? = ver

        if (latestVersion == null) {
            latestVersion = documentRepository.findTopVersionByTitle(doc)
                ?: throw DocumentNotFoundException()
        }

        return documentRepository.findByTitleAndVersion(doc, latestVersion)
            ?: throw DocumentNotFoundException()
    }

    @Transactional
    fun getDocumentsByCategory(
        categoryName: String
    ): DocumentListResponse {

        val category = categoryRepository.findByName(categoryName)
            ?: throw CategoryNotFoundException()

        val documents = documentRepository.findAllByCategory(category)
            ?: throw DocumentNotFoundException()
        val documentsListResponse = mutableListOf<DocumentResponse>()

        //title 별로 최신버전만 가져오기
        val latestDocuments = documents.groupBy { it.title }
            .map { entry -> entry.value.maxBy { it.version } }

        documentsListResponse.addAll(
            latestDocuments.map { document ->
                DocumentResponse(
                    title = document.title,
                    createdAt = document.createdAt
                )
            }
        )
        return DocumentListResponse(documentsListResponse)
    }

    @Transactional
    fun getAllVersionsByDocument(
        documentVersionsRequest: DocumentVersionsRequest
    ): DocumentListResponse {
        val documentTitle = documentVersionsRequest.title
        val documents = documentRepository.findAllByTitle(documentTitle)
            ?: throw DocumentNotFoundException()

        val documentsListResponse = mutableListOf<DocumentResponse>()
        documentsListResponse.addAll(
            documents.map { document ->
                DocumentResponse(
                    title = document.title,
                    createdAt = document.createdAt
                )
            }
        )

        return DocumentListResponse(documentsListResponse)
    }

    @Transactional
    fun getAllDocuments(): DocumentListResponse {
        val documents = documentRepository.findAllLatestByTitle()
        val documentsListResponse = mutableListOf<DocumentResponse>()
        documentsListResponse.addAll(
            documents.map { document ->
                DocumentResponse(
                    title = document.title,
                    createdAt = document.createdAt
                )
            }
        )
        return DocumentListResponse(documentsListResponse)
    }

    @Transactional
    fun createCategory(
        memberId: UUID,
        categoryCreationRequest: CategoryCreationRequest
    ): Category {

        val member = memberRepository.findById(memberId)
            ?: throw MemberNotFoundException()

        return categoryRepository.save(
            Category(
                name = categoryCreationRequest.name,
                description = categoryCreationRequest.description
            )
        )
    }

    @Transactional
    fun getAllCategories(): List<Category> {
        return categoryRepository.findAll()
    }
}