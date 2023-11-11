package com.ajouin.ajouin_be.domain.news.service.crawler.department.math

import com.ajouin.ajouin_be.domain.news.service.crawler.NoticeCrawlerStrategy
import com.ajouin.ajouin_be.domain.news.domain.SchoolNotice
import com.ajouin.ajouin_be.domain.news.domain.Type
import com.ajouin.ajouin_be.domain.news.service.utils.notice.Type1Utils
import com.ajouin.ajouin_be.domain.news.service.utils.notice.Type4Utils
import org.jsoup.nodes.Element
import org.springframework.stereotype.Component

@Component
class MathEmploymentNoticeStrategy : NoticeCrawlerStrategy {
    override val url: String = "https://www2.ajou.ac.kr/math/board/board03.jsp"
    override val type: Type = Type.수학과1
    override val selector: String = Type4Utils.SELECTOR

    override fun parseNotice(row: Element, lastId: Long): SchoolNotice? {
        return Type4Utils.parseNotice(type, row, lastId)
    }
    override fun getIfTopFixedNotice(row: Element): SchoolNotice? {
        return Type4Utils.getIfTopFixedNotice(type, row)
    }
}