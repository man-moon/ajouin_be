package com.ajouin.ajouin_be.domain.news.service.crawler.department

import com.ajouin.ajouin_be.domain.news.service.crawler.NoticeCrawlerStrategy
import com.ajouin.ajouin_be.domain.news.domain.SchoolNotice
import com.ajouin.ajouin_be.domain.news.domain.Type
import com.ajouin.ajouin_be.domain.news.service.utils.notice.Type1Utils
import com.ajouin.ajouin_be.domain.news.service.utils.notice.Type7Utils
import org.jsoup.nodes.Element
import org.springframework.stereotype.Component

@Component
class AiMobilityEngineering : NoticeCrawlerStrategy {
    override val url: String = "https://mobility.ajou.ac.kr/mobility/board/notice.do"
    override val type: Type = Type.AI모빌리티공학과
    override val selector: String = Type7Utils.SELECTOR

    override fun parseNotice(row: Element, lastId: Long): SchoolNotice? {
        return Type7Utils.parseNotice(type, row, lastId)
    }
    override fun getIfTopFixedNotice(row: Element): SchoolNotice? {
        return Type7Utils.getIfTopFixedNotice(type, row)
    }
}

