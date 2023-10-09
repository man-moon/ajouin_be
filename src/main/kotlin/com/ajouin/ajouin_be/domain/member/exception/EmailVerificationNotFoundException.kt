package com.ajouin.ajouin_be.domain.member.exception

import com.ajouin.ajouin_be.global.error.exception.EntityNotFoundException
import com.ajouin.ajouin_be.global.error.exception.ErrorCode

class EmailVerificationNotFoundException: EntityNotFoundException(ErrorCode.EMAIL_VERIFICATION_NOT_FOUND)