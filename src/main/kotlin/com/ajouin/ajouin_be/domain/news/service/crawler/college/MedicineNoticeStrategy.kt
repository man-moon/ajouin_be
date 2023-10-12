package com.ajouin.ajouin_be.domain.news.service.crawler.college

import com.ajouin.ajouin_be.domain.news.service.crawler.NoticeCrawlerStrategy
import com.ajouin.ajouin_be.domain.news.domain.SchoolNotice
import com.ajouin.ajouin_be.domain.news.domain.Type
import com.ajouin.ajouin_be.domain.news.service.utils.notice.Type10Utils
import org.jsoup.nodes.Element
import org.springframework.stereotype.Component

@Component
class MedicineNoticeStrategy : NoticeCrawlerStrategy {
    override val url: String = "https://www.ajoumc.or.kr/medicine/board/commBoardUVNoticeList.do"
    override val type: Type = Type.의과대학
    override val selector: String = Type10Utils.SELECTOR

    override fun parseNotice(row: Element, lastId: Long): SchoolNotice? {
        return Type10Utils.parseNotice(type, row, lastId)
    }
}