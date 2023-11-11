package com.ajouin.ajouin_be.domain.news.service.crawler

import com.ajouin.ajouin_be.domain.news.domain.SchoolNotice
import com.ajouin.ajouin_be.domain.news.domain.Type
import org.jsoup.nodes.Element

interface NoticeCrawlerStrategy {
    val url: String
    val selector: String
    val type: Type

    fun parseNotice(row: Element, lastId: Long): SchoolNotice?
    fun getIfTopFixedNotice(row: Element): SchoolNotice?
}
