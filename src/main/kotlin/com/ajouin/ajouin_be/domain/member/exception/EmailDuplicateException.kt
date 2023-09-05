package com.ajouin.ajouin_be.domain.member.exception

import com.ajouin.ajouin_be.global.error.exception.ErrorCode
import com.ajouin.ajouin_be.global.error.exception.InvalidValueException

class EmailDuplicateException: InvalidValueException(ErrorCode.EMAIL_DUPLICATION)