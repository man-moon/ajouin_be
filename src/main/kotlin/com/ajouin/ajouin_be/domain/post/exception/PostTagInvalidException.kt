package com.ajouin.ajouin_be.domain.post.exception

import com.ajouin.ajouin_be.global.error.exception.ErrorCode
import com.ajouin.ajouin_be.global.error.exception.InvalidValueException

class PostTagInvalidException: InvalidValueException(ErrorCode.POST_TAG_INVALID)