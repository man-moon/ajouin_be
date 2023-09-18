package com.ajouin.ajouin_be.domain.news.service.utils.type

import com.ajouin.ajouin_be.domain.news.domain.SchoolNotice
import com.ajouin.ajouin_be.domain.news.domain.Type
import com.ajouin.ajouin_be.domain.news.service.utils.Utils
import org.jsoup.nodes.Element
import java.text.SimpleDateFormat
import java.util.*

//신버전메인
class Type1Utils {
    companion object {
        const val SELECTOR = "#cms-content > div > div > div.bn-list-common02.type01.bn-common-cate > table > tbody > tr"

        fun parseNotice(type: Type, row: Element, lastId: Long): SchoolNotice? {
            //공지 자체 번호
            val num = row.select("td.b-num-box").text()
            val title = row.select("td.b-td-left > div > a").text()
            val link = row.select("td.b-td-left > div > a").attr("href")
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val date: Date = dateFormat.parse(row.select("td:last-child").text())

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