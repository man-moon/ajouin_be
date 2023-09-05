package com.ajouin.ajouin_be.global.config.security.exception

import com.ajouin.ajouin_be.global.error.exception.ErrorCode

class InvalidTokenException: SecurityException(ErrorCode.INVALID_TOKEN)