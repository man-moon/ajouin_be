package com.ajouin.ajouin_be.global.error.exception

open class InvalidValueException(
    errorCode: ErrorCode = ErrorCode.INVALID_INPUT_VALUE
): BusinessException(errorCode)