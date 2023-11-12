package com.ajouin.ajouin_be.domain.wiki.exception

import com.ajouin.ajouin_be.global.error.exception.EntityNotFoundException
import com.ajouin.ajouin_be.global.error.exception.ErrorCode

class DocumentNotFoundException: EntityNotFoundException(
    ErrorCode.DOCUMENT_NOT_FOUND
)