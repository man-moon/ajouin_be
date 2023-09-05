package com.ajouin.ajouin_be.global.config.security

data class TokenInfo(
    val grantType: String,
    val accessToken: String,
)