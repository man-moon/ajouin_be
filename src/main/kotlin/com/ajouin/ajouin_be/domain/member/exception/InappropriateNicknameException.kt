package com.ajouin.ajouin_be.domain.member.exception

import com.ajouin.ajouin_be.global.error.exception.ErrorCode
import com.ajouin.ajouin_be.global.error.exception.InvalidValueException

class InappropriateNicknameException: InvalidValueException(ErrorCode.INAPPROPRIATE_NICKNAME) {
}