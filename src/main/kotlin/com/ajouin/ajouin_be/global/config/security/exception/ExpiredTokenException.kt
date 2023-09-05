package com.ajouin.ajouin_be.global.config.security.exception

import com.ajouin.ajouin_be.global.error.exception.ErrorCode

class ExpiredTokenException: SecurityException(ErrorCode.TOKEN_EXPIRED) {
}