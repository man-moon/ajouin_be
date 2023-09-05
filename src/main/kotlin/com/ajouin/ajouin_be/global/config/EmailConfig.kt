package com.ajouin.ajouin_be.global.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "email.verification")
class EmailConfig {
    lateinit var subject: String
    lateinit var text: String
}