package com.ajouin.ajouin_be.domain.news.service.utils.notice

import com.ajouin.ajouin_be.domain.news.domain.SchoolNotice
import com.ajouin.ajouin_be.domain.news.domain.Type
import com.ajouin.ajouin_be.domain.news.service.utils.Utils
import org.jsoup.nodes.Element
import java.text.SimpleDateFormat
import java.util.*

//소웨 자체 공지
class Type2Utils {
    companion object {
        const val SELECTOR = "#sub_contents > div > div.conbody > table:nth-child(2) > tbody > tr"

        fun parseNotice(type: Type, row: Element, lastId: Long): SchoolNotice? {
            if(row.attr("height") != "45") return null

            var num: String = "0"

            val isTopFixed = row.select("td > img[src=\"/skin/bbs/basic_responsive_new/images/btn_notice.gif\"]").firstOrNull()
            if(isTopFixed != null) {
                num = "공지"
            }

            val titleAndLinkElement = row.select("td.responsive03 > a")
            val title = titleAndLinkElement.text().trim()
            val link = titleAndLinkElement.attr("href")

            if(title == "") return null

            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val dateText = row.select("td.responsive03 > p.tablet_regist_date").text()
            val date = dateFormat.parse(dateText) ?: Date()

            val id = Utils.getPostId(link)

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