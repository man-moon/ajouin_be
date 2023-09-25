package com.ajouin.ajouin_be.global.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "instagram")
class InstagramConfig {
    lateinit var username1: String
    lateinit var password1: String
    lateinit var username2: String
    lateinit var password2: String
}