package com.ajouin.ajouin_be.domain.news.service.crawler.department.architecture

import com.ajouin.ajouin_be.domain.news.service.crawler.NoticeCrawlerStrategy
import com.ajouin.ajouin_be.domain.news.domain.SchoolNotice
import com.ajouin.ajouin_be.domain.news.domain.Type
import com.ajouin.ajouin_be.domain.news.service.utils.notice.Type3Utils
import org.jsoup.nodes.Element
import org.springframework.stereotype.Component

@Component
class ArchitectureIntegratedNoticeStrategy : NoticeCrawlerStrategy {
    override val url: String = "https://arch.ajou.ac.kr/arch/board/board01.jsp"
    override val type: Type = Type.건축학과0
    override val selector: String = Type3Utils.SELECTOR

    override fun parseNotice(row: Element, lastId: Long): SchoolNotice? {
        return Type3Utils.parseNotice(type, row, lastId)
    }
}