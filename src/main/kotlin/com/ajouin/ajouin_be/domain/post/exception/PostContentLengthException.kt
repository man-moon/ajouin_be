package com.ajouin.ajouin_be.domain.post.exception

import com.ajouin.ajouin_be.global.error.exception.ErrorCode
import com.ajouin.ajouin_be.global.error.exception.InvalidValueException

class PostContentLengthException: InvalidValueException(ErrorCode.POST_CONTENT_LENGTH_INVALID)