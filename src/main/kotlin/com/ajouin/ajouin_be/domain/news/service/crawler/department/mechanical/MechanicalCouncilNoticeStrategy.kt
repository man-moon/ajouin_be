package com.ajouin.ajouin_be.domain.news.service.crawler.department.mechanical

import com.ajouin.ajouin_be.domain.news.service.crawler.NoticeCrawlerStrategy
import com.ajouin.ajouin_be.domain.news.domain.SchoolNotice
import com.ajouin.ajouin_be.domain.news.domain.Type
import com.ajouin.ajouin_be.domain.news.service.utils.type.Type4Utils
import org.jsoup.nodes.Element
import org.springframework.stereotype.Component

@Component
class MechanicalCouncilNoticeStrategy : NoticeCrawlerStrategy {
    override val url: String = "https://me.ajou.ac.kr/me/board/board05.jsp"
    override val type: Type = Type.기계공학과1
    override val selector: String = Type4Utils.SELECTOR

    override fun parseNotice(row: Element, lastId: Long): SchoolNotice? {
        return Type4Utils.parseNotice(type, row, lastId)
    }
}