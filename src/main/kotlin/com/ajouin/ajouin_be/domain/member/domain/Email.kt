package com.ajouin.ajouin_be.domain.member.domain

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotEmpty

@Embeddable
class Email(
    @Email
    @Column(name = "email")
    @NotEmpty
    val value: String,
) {
    fun getDomain(): String {
        return value.split("@")[1]
    }
    fun getId(): String {
        return value.split("@")[0]
    }

    fun isNotAjouUnivEmail(): Boolean {
        val regex = """^[a-zA-Z0-9._-]+@ajou\.ac\.kr$""".toRegex()
        return !regex.matches(value)
    }
}