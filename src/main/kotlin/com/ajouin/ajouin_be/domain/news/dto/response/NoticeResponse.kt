package com.ajouin.ajouin_be.domain.news.dto.response

import com.ajouin.ajouin_be.domain.news.domain.BookMark
import com.ajouin.ajouin_be.domain.news.domain.LatestUpdateTime
import com.ajouin.ajouin_be.domain.news.domain.SchoolNotice
import java.time.LocalDateTime

data class NoticeResponse (
    val notices: List<SchoolNotice>,
    val bookMarks: List<SchoolNotice>? = null,
    val latestUpdateTime: LocalDateTime = LatestUpdateTime.latestUpdateTime,
)