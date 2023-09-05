package com.ajouin.ajouin_be.global.error.exception

open class HandleAccessException(
        errorCode: ErrorCode = ErrorCode.HANDLE_ACCESS_DENIED
) : BusinessException(errorCode)