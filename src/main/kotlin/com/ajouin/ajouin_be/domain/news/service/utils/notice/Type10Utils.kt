package com.ajouin.ajouin_be.domain.news.service.utils.notice

import com.ajouin.ajouin_be.domain.news.domain.SchoolNotice
import com.ajouin.ajouin_be.domain.news.domain.Type
import com.ajouin.ajouin_be.domain.news.service.utils.Utils
import org.jsoup.nodes.Element
import org.slf4j.LoggerFactory
import java.text.SimpleDateFormat
import java.util.*

//의과대학
class Type10Utils {
    companion object {
        const val SELECTOR = "#contents > article > section > div > div.tb_w > table > tbody > tr"

        fun parseNotice(type: Type, row: Element, lastId: Long): SchoolNotice? {
            //공지 자체 번호
            val num = row.select("td.td_num").text()
            val title = row.select("td.td_qna > a > span").text()
            val id = row.select("td.td_qna > a").attr("href").substringAfter("no: ").substringBefore(" } )").toLongOrNull() ?: return null
            val link = "https://www.ajoumc.or.kr/medicine/board/commBoardUVNoticeView.do?no=$id"
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val date: Date = dateFormat.parse(row.select("td.td_date").text())

            //마지막 저장된 id값 비교
            return if (id <= lastId) null
            else {
                SchoolNotice(
                    title = title,
                    link = link,
                    type = type,
                    isTopFixed = num == "공지",
                    date = date,
                    fetchId = id,
                )
            }
        }
    }
}