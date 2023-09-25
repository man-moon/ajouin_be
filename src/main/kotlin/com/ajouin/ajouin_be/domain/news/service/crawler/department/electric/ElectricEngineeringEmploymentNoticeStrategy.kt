package com.ajouin.ajouin_be.domain.news.service.crawler.department.electric

import com.ajouin.ajouin_be.domain.news.service.crawler.NoticeCrawlerStrategy
import com.ajouin.ajouin_be.domain.news.domain.SchoolNotice
import com.ajouin.ajouin_be.domain.news.domain.Type
import com.ajouin.ajouin_be.domain.news.service.utils.notice.Type6Utils
import org.jsoup.nodes.Element
import org.springframework.stereotype.Component

@Component
class ElectricEngineeringEmploymentNoticeStrategy : NoticeCrawlerStrategy {
    override val url: String = "https://it.ajou.ac.kr/ece/job/job-announcement.do"
    override val type: Type = Type.전자공학과1
    override val selector: String = Type6Utils.SELECTOR

    override fun parseNotice(row: Element, lastId: Long): SchoolNotice? {
        return Type6Utils.parseNotice(type, row, lastId)
    }
}