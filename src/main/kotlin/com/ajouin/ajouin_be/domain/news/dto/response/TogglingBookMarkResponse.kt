package com.ajouin.ajouin_be.domain.news.dto.response

data class TogglingBookMarkResponse (
    val noticeId: Long,
    val isBookMarked: Boolean,
)