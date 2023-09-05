package com.ajouin.ajouin_be.domain.member.exception

import com.ajouin.ajouin_be.global.error.exception.ErrorCode
import com.ajouin.ajouin_be.global.error.exception.InvalidValueException

class InvalidCredentialsException(
): InvalidValueException(ErrorCode.LOGIN_INPUT_INVALID)