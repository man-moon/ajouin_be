package com.ajouin.ajouin_be.domain.news.service.crawler.college

import com.ajouin.ajouin_be.domain.news.service.crawler.NoticeCrawlerStrategy
import com.ajouin.ajouin_be.domain.news.domain.SchoolNotice
import com.ajouin.ajouin_be.domain.news.domain.Type
import com.ajouin.ajouin_be.domain.news.service.utils.type.Type1Utils
import org.jsoup.nodes.Element
import org.springframework.stereotype.Component

@Component
class HumanityNoticeStrategy : NoticeCrawlerStrategy {
    override val url: String = "https://human.ajou.ac.kr/human/community/community01.do"
    override val type: Type = Type.인문대학
    override val selector: String = Type1Utils.SELECTOR

    override fun parseNotice(row: Element, lastId: Long): SchoolNotice? {
        return Type1Utils.parseNotice(type, row, lastId)
    }
}