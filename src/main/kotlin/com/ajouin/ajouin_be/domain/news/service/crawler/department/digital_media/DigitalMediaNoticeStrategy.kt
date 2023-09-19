package com.ajouin.ajouin_be.domain.news.service.crawler.department.digital_media

import com.ajouin.ajouin_be.domain.news.service.crawler.NoticeCrawlerStrategy
import com.ajouin.ajouin_be.domain.news.domain.SchoolNotice
import com.ajouin.ajouin_be.domain.news.domain.Type
import com.ajouin.ajouin_be.domain.news.service.utils.notice_type.Type3Utils
import org.jsoup.nodes.Element
import org.springframework.stereotype.Component

@Component
class DigitalMediaNoticeStrategy : NoticeCrawlerStrategy {
    override val url: String = "https://media.ajou.ac.kr/media/board/board01.jsp"
    override val type: Type = Type.디지털미디어학과0
    override val selector: String = Type3Utils.SELECTOR

    override fun parseNotice(row: Element, lastId: Long): SchoolNotice? {
        return Type3Utils.parseNotice(type, row, lastId)
    }
}