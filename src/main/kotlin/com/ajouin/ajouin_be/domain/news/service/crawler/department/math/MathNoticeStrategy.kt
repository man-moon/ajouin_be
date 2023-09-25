package com.ajouin.ajouin_be.domain.news.service.crawler.department.math

import com.ajouin.ajouin_be.domain.news.service.crawler.NoticeCrawlerStrategy
import com.ajouin.ajouin_be.domain.news.domain.SchoolNotice
import com.ajouin.ajouin_be.domain.news.domain.Type
import com.ajouin.ajouin_be.domain.news.service.utils.notice.Type3Utils
import org.jsoup.nodes.Element
import org.springframework.stereotype.Component

@Component
class MathNoticeStrategy : NoticeCrawlerStrategy {
    override val url: String = "https://www2.ajou.ac.kr/math/board/board01.jsp"
    override val type: Type = Type.수학과0
    override val selector: String = Type3Utils.SELECTOR

    override fun parseNotice(row: Element, lastId: Long): SchoolNotice? {
        return Type3Utils.parseNotice(type, row, lastId)
    }
}