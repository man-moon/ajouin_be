package com.ajouin.ajouin_be.domain.member.exception

import com.ajouin.ajouin_be.global.error.exception.EntityNotFoundException
import com.ajouin.ajouin_be.global.error.exception.ErrorCode


class EmailNotFoundException: EntityNotFoundException(ErrorCode.ENTITY_NOT_FOUND)