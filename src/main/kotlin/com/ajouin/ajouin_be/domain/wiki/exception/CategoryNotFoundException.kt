package com.ajouin.ajouin_be.domain.wiki.exception

import com.ajouin.ajouin_be.global.error.exception.EntityNotFoundException
import com.ajouin.ajouin_be.global.error.exception.ErrorCode

class CategoryNotFoundException: EntityNotFoundException(ErrorCode.CATEGORY_NOT_FOUND)