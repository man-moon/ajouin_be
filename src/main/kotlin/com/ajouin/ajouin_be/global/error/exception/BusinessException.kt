package com.ajouin.ajouin_be.global.error.exception

open class BusinessException(val errorCode: ErrorCode = ErrorCode.INTERNAL_SERVER_ERROR): RuntimeException()
