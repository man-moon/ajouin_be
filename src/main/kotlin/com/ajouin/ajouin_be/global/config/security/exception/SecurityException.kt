package com.ajouin.ajouin_be.global.config.security.exception

import com.ajouin.ajouin_be.global.error.exception.ErrorCode

open class SecurityException(val errorCode: ErrorCode = ErrorCode.NOT_VERIFIED): RuntimeException()