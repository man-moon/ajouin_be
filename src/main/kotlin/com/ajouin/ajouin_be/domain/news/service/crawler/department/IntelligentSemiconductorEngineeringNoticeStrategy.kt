package com.ajouin.ajouin_be.domain.news.service.crawler.department

import com.ajouin.ajouin_be.domain.news.service.crawler.NoticeCrawlerStrategy
import com.ajouin.ajouin_be.domain.news.domain.SchoolNotice
import com.ajouin.ajouin_be.domain.news.domain.Type
import com.ajouin.ajouin_be.domain.news.service.utils.type.Type8Utils
import org.jsoup.nodes.Element
import org.springframework.stereotype.Component

@Component
class IntelligentSemiconductorEngineeringNoticeStrategy : NoticeCrawlerStrategy {
    override val url: String = "https://aisemi.ajou.ac.kr/aisemi/board/notice.do"
    override val type: Type = Type.지능형반도체공학과
    override val selector: String = Type8Utils.SELECTOR

    override fun parseNotice(row: Element, lastId: Long): SchoolNotice? {
        return Type8Utils.parseNotice(type, row, lastId)
    }
}