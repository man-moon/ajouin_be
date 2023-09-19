package com.ajouin.ajouin_be.domain.news.service.crawler.department.chemical_engineering

import com.ajouin.ajouin_be.domain.news.service.crawler.NoticeCrawlerStrategy
import com.ajouin.ajouin_be.domain.news.domain.SchoolNotice
import com.ajouin.ajouin_be.domain.news.domain.Type
import com.ajouin.ajouin_be.domain.news.service.utils.notice_type.Type6Utils
import org.jsoup.nodes.Element
import org.springframework.stereotype.Component

@Component
class ChemicalEngineeringEmploymentNoticeStrategy : NoticeCrawlerStrategy {
    override val url: String = "https://www.ajou.ac.kr/che/board/employment.do"
    override val type: Type = Type.화학공학과1
    override val selector: String = Type6Utils.SELECTOR

    override fun parseNotice(row: Element, lastId: Long): SchoolNotice? {
        return Type6Utils.parseNotice(type, row, lastId)
    }
}