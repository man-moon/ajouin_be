package com.ajouin.ajouin_be.domain.news.exception

import com.ajouin.ajouin_be.global.error.exception.EntityNotFoundException
import com.ajouin.ajouin_be.global.error.exception.ErrorCode

class NoticeNotFoundException: EntityNotFoundException(ErrorCode.SCHOOL_NOTICE_NOT_FOUND)