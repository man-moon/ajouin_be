package com.ajouin.ajouin_be.domain.news.dto.response

import com.ajouin.ajouin_be.domain.news.domain.InstagramNotice

data class InstagramNoticeResponse (
    val notices: List<InstagramNotice>
)