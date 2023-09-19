package com.ajouin.ajouin_be.domain.news.service.crawler.department

import com.ajouin.ajouin_be.domain.news.service.crawler.NoticeCrawlerStrategy
import com.ajouin.ajouin_be.domain.news.domain.SchoolNotice
import com.ajouin.ajouin_be.domain.news.domain.Type
import com.ajouin.ajouin_be.domain.news.service.utils.notice_type.Type3Utils
import org.jsoup.nodes.Element
import org.springframework.stereotype.Component

@Component
class DefenseDigitalConvergence : NoticeCrawlerStrategy {
    override val url: String = "https://mdc.ajou.ac.kr/mdc/board/board01.jsp"
    override val type: Type = Type.국방디지털융합학과
    override val selector: String = Type3Utils.SELECTOR

    override fun parseNotice(row: Element, lastId: Long): SchoolNotice? {
        return Type3Utils.parseNotice(type, row, lastId)
    }
}
