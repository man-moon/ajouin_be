package com.ajouin.ajouin_be.domain.news.service.crawler.department

import com.ajouin.ajouin_be.domain.news.service.crawler.NoticeCrawlerStrategy
import com.ajouin.ajouin_be.domain.news.domain.SchoolNotice
import com.ajouin.ajouin_be.domain.news.domain.Type
import com.ajouin.ajouin_be.domain.news.service.utils.notice.Type1Utils
import com.ajouin.ajouin_be.domain.news.service.utils.notice.Type3Utils
import org.jsoup.nodes.Element
import org.springframework.stereotype.Component

@Component
class CulturalContentsNoticeStrategy : NoticeCrawlerStrategy {
    override val url: String = "https://culture.ajou.ac.kr/culture/board/board01.jsp"
    override val type: Type = Type.문화콘텐츠학과
    override val selector: String = Type3Utils.SELECTOR

    override fun parseNotice(row: Element, lastId: Long): SchoolNotice? {
        return Type3Utils.parseNotice(type, row, lastId)
    }
    override fun getIfTopFixedNotice(row: Element): SchoolNotice? {
        return Type3Utils.getIfTopFixedNotice(type, row)
    }
}
