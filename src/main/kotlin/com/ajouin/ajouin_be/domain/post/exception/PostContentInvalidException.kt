package com.ajouin.ajouin_be.domain.post.exception

import com.ajouin.ajouin_be.global.error.exception.ErrorCode
import com.ajouin.ajouin_be.global.error.exception.InvalidValueException

class PostContentInvalidException: InvalidValueException(ErrorCode.POST_CONTENT_VALUE_INVALID)