package com.ajouin.ajouin_be.domain.member.exception

import com.ajouin.ajouin_be.global.error.exception.EntityNotFoundException
import com.ajouin.ajouin_be.global.error.exception.ErrorCode

class MemberNotFoundException: EntityNotFoundException(ErrorCode.MEMBER_NOT_FOUND)