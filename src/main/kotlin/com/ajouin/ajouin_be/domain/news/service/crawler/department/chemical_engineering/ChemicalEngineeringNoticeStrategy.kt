package com.ajouin.ajouin_be.domain.news.service.crawler.department.chemical_engineering

import com.ajouin.ajouin_be.domain.news.service.crawler.NoticeCrawlerStrategy
import com.ajouin.ajouin_be.domain.news.domain.SchoolNotice
import com.ajouin.ajouin_be.domain.news.domain.Type
import com.ajouin.ajouin_be.domain.news.service.utils.notice.Type1Utils
import org.jsoup.nodes.Element
import org.springframework.stereotype.Component

@Component
class ChemicalEngineeringNoticeStrategy : NoticeCrawlerStrategy {
    override val url: String = "https://www.ajou.ac.kr/che/board/notice.do"
    override val type: Type = Type.화학공학과0
    override val selector: String = Type1Utils.SELECTOR

    override fun parseNotice(row: Element, lastId: Long): SchoolNotice? {
        return Type1Utils.parseNotice(type, row, lastId)
    }
    override fun getIfTopFixedNotice(row: Element): SchoolNotice? {
        return Type1Utils.getIfTopFixedNotice(type, row)
    }
}