package com.ajouin.ajouin_be.domain.news.dto.response

import com.ajouin.ajouin_be.domain.news.domain.SchoolNotice

data class NoticeResponse (
    val notices: List<SchoolNotice>
)