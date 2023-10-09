package com.ajouin.ajouin_be.domain.news.domain

import java.time.LocalDateTime

class LatestUpdateTime {
    companion object {
        var latestUpdateTime: LocalDateTime = LocalDateTime.now()
    }
}