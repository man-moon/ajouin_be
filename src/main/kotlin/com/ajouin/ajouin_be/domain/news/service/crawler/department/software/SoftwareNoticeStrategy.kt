package com.ajouin.ajouin_be.domain.news.service.crawler.department.software

import com.ajouin.ajouin_be.domain.news.service.crawler.NoticeCrawlerStrategy
import com.ajouin.ajouin_be.domain.news.domain.SchoolNotice
import com.ajouin.ajouin_be.domain.news.domain.Type
import com.ajouin.ajouin_be.domain.news.service.utils.notice.Type1Utils
import com.ajouin.ajouin_be.domain.news.service.utils.notice.Type2Utils
import org.jsoup.nodes.Element
import org.springframework.stereotype.Component

@Component
class SoftwareNoticeStrategy : NoticeCrawlerStrategy {
    override val url: String = "http://software.ajou.ac.kr/bbs/board.php?tbl=notice"
    override val type: Type = Type.소프트웨어학과0
    override val selector: String = Type2Utils.SELECTOR

    override fun parseNotice(row: Element, lastId: Long): SchoolNotice? {
        return Type2Utils.parseNotice(type, row, lastId)
    }
    override fun getIfTopFixedNotice(row: Element): SchoolNotice? {
        return Type2Utils.getIfTopFixedNotice(type, row)
    }
}