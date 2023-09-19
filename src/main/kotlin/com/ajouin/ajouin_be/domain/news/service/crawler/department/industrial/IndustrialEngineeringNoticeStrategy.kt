package com.ajouin.ajouin_be.domain.news.service.crawler.department.industrial

import com.ajouin.ajouin_be.domain.news.service.crawler.NoticeCrawlerStrategy
import com.ajouin.ajouin_be.domain.news.domain.SchoolNotice
import com.ajouin.ajouin_be.domain.news.domain.Type
import com.ajouin.ajouin_be.domain.news.service.utils.notice_type.Type1Utils
import org.jsoup.nodes.Element
import org.springframework.stereotype.Component

@Component
class IndustrialEngineeringNoticeStrategy : NoticeCrawlerStrategy {
    override val url: String = "https://www.ajou.ac.kr/ie/academic/notice.do"
    override val type: Type = Type.산업공학과0
    override val selector: String = Type1Utils.SELECTOR

    override fun parseNotice(row: Element, lastId: Long): SchoolNotice? {
        return Type1Utils.parseNotice(type, row, lastId)
    }
}