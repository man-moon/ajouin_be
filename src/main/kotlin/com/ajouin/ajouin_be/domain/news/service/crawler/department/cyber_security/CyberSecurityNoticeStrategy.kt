package com.ajouin.ajouin_be.domain.news.service.crawler.department.cyber_security

import com.ajouin.ajouin_be.domain.news.service.crawler.NoticeCrawlerStrategy
import com.ajouin.ajouin_be.domain.news.domain.SchoolNotice
import com.ajouin.ajouin_be.domain.news.domain.Type
import com.ajouin.ajouin_be.domain.news.service.utils.notice.Type3Utils
import org.jsoup.nodes.Element
import org.springframework.stereotype.Component

@Component
class CyberSecurityNoticeStrategy : NoticeCrawlerStrategy {
    override val url: String = "https://security.ajou.ac.kr/security/board/board01.jsp"
    override val type: Type = Type.사이버보안학과0
    override val selector: String = Type3Utils.SELECTOR

    override fun parseNotice(row: Element, lastId: Long): SchoolNotice? {
        return Type3Utils.parseNotice(type, row, lastId)
    }
}