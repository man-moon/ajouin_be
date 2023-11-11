package com.ajouin.ajouin_be.domain.news.service.crawler.college

import com.ajouin.ajouin_be.domain.news.service.crawler.NoticeCrawlerStrategy
import com.ajouin.ajouin_be.domain.news.domain.SchoolNotice
import com.ajouin.ajouin_be.domain.news.domain.Type
import com.ajouin.ajouin_be.domain.news.service.utils.notice.Type1Utils
import org.jsoup.nodes.Element
import org.springframework.stereotype.Component

@Component
class EngineeringNoticeStrategy : NoticeCrawlerStrategy {
    override val url: String = "https://eng.ajou.ac.kr/eng/community/notice.do"
    override val type: Type = Type.공과대학
    override val selector: String = Type1Utils.SELECTOR

    override fun parseNotice(row: Element, lastId: Long): SchoolNotice? {
        return Type1Utils.parseNotice(type, row, lastId)
    }
    override fun getIfTopFixedNotice(row: Element): SchoolNotice? {
        return Type1Utils.getIfTopFixedNotice(type, row)
    }
}