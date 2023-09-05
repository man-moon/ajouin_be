package com.ajouin.ajouin_be.global.error.exception

open class EntityNotFoundException(
    errorCode: ErrorCode = ErrorCode.ENTITY_NOT_FOUND
) : BusinessException(errorCode)