package com.ajouin.ajouin_be.domain.news.service.utils

class Utils {
    companion object {
        fun getPostId(link: String) = (link.substringAfter("articleNo=", "").substringBefore("&", "").toLongOrNull()
            ?: link.substringAfter("article_no=", "").substringBefore("&", "").toLongOrNull()
            ?: link.substringAfter("num=", "").substringBefore("&", "").toLongOrNull()
            ?: throw Exception("공지사항 id를 찾을 수 없습니다."))
    }
}