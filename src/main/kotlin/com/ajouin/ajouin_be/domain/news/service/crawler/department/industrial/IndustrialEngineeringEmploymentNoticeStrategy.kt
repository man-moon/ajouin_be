package com.ajouin.ajouin_be.domain.news.service.crawler.department.industrial

import com.ajouin.ajouin_be.domain.news.service.crawler.NoticeCrawlerStrategy
import com.ajouin.ajouin_be.domain.news.domain.SchoolNotice
import com.ajouin.ajouin_be.domain.news.domain.Type
import com.ajouin.ajouin_be.domain.news.service.utils.notice.Type1Utils
import com.ajouin.ajouin_be.domain.news.service.utils.notice.Type5Utils
import org.jsoup.nodes.Element
import org.springframework.stereotype.Component

@Component
class IndustrialEngineeringEmploymentNoticeStrategy : NoticeCrawlerStrategy {
    override val url: String = "https://www.ajou.ac.kr/ie/academic/job.do"
    override val type: Type = Type.산업공학과2
    override val selector: String = Type5Utils.SELECTOR

    override fun parseNotice(row: Element, lastId: Long): SchoolNotice? {
        return Type5Utils.parseNotice(type, row, lastId)
    }
    override fun getIfTopFixedNotice(row: Element): SchoolNotice? {
        return Type5Utils.getIfTopFixedNotice(type, row)
    }
}