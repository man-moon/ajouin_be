package com.ajouin.ajouin_be.domain.news.service.crawler.college

import com.ajouin.ajouin_be.domain.news.service.crawler.NoticeCrawlerStrategy
import com.ajouin.ajouin_be.domain.news.domain.SchoolNotice
import com.ajouin.ajouin_be.domain.news.domain.Type
import com.ajouin.ajouin_be.domain.news.service.utils.notice.Type1Utils
import com.ajouin.ajouin_be.domain.news.service.utils.notice.Type9Utils
import org.jsoup.nodes.Element
import org.springframework.stereotype.Component

@Component
class NursingNoticeStrategy : NoticeCrawlerStrategy {
    override val url: String = "https://www.ajoumc.or.kr/nursing/"
    override val type: Type = Type.간호대학
    override val selector: String = Type9Utils.SELECTOR

    override fun parseNotice(row: Element, lastId: Long): SchoolNotice? {
        return Type9Utils.parseNotice(type, row, lastId)
    }
    override fun getIfTopFixedNotice(row: Element): SchoolNotice? {
        return Type9Utils.getIfTopFixedNotice(type, row)
    }
}