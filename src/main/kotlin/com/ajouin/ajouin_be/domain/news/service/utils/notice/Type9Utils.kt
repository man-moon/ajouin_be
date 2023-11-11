package com.ajouin.ajouin_be.domain.news.service.utils.notice

import com.ajouin.ajouin_be.domain.news.domain.SchoolNotice
import com.ajouin.ajouin_be.domain.news.domain.Type
import com.ajouin.ajouin_be.domain.news.service.utils.Utils
import org.jsoup.nodes.Element
import java.text.SimpleDateFormat
import java.util.*

//간호대학
//이걸로 변경 고려
//https://www.ajoumc.or.kr/nursing/board/commBoardNRNewsList.do
class Type9Utils {

    companion object {
        const val SELECTOR = "#nt-con-1 a"

        fun parseNotice(type: Type, row: Element, lastId: Long): SchoolNotice? {
            //공지 자체 번호
            val id = row.select("a").attr("href").substringAfter("no=").toLongOrNull() ?: return null
            val title = row.select("a .ntp-inner strong").text()
            val link = row.select("a").attr("href")
            val modifiedLink = link.takeIf { it.startsWith("/nursing/") }?.replaceFirst("/nursing/", "") ?: link
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val date: Date = dateFormat.parse(row.select("a .ntp-inner .con span").text()) ?: return null

            //마지막 저장된 id값 비교
            return if (id <= lastId) null
            else {
                SchoolNotice(
                    title = title,
                    link = modifiedLink,
                    type = type,
                    isTopFixed = false,
                    date = date,
                    fetchId = id,
                )
            }
        }

        fun getIfTopFixedNotice(type: Type, row: Element): SchoolNotice? {
            return null
        }
    }
}