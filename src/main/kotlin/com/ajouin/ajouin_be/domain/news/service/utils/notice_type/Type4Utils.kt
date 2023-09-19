package com.ajouin.ajouin_be.domain.news.service.utils.notice_type

import com.ajouin.ajouin_be.domain.news.domain.SchoolNotice
import com.ajouin.ajouin_be.domain.news.domain.Type
import com.ajouin.ajouin_be.domain.news.service.utils.Utils.Companion.getPostId
import org.jsoup.nodes.Element
import java.text.SimpleDateFormat
import java.util.*

//옛날버전 + 서브공지(ex. 취업게시판)
class Type4Utils {
    companion object {
        const val SELECTOR = "#jwxe_main_content > div > div.list_wrap > table > tbody > tr"

        fun parseNotice(type: Type, row: Element, lastId: Long): SchoolNotice? {
            //공지 자체 번호
            val title = row.select("td > a").text().trim()
            val link = row.select("td > a").attr("href")
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val date: Date = dateFormat.parse(row.select("td:nth-child(5)").text().trim())

            val id = getPostId(link)

            //마지막 저장된 id값 비교
            return if (id <= lastId) null
            else {
                SchoolNotice(
                    title = title,
                    link = link,
                    type = type,
                    isTopFixed = false,
                    date = date,
                    fetchId = id,
                )
            }
        }
    }
}