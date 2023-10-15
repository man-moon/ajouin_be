package com.ajouin.ajouin_be.domain.member.service

import com.ajouin.ajouin_be.domain.member.domain.Member
import com.ajouin.ajouin_be.domain.member.exception.MemberNotFoundException
import com.ajouin.ajouin_be.domain.member.repository.MemberRepository
import com.slack.api.Slack
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class MemberServiceImpl(
    private val memberRepository: MemberRepository,
) {
    @Value("\${slack.webhook.url}")
    lateinit var webHookUrl: String

    fun getMemberInfo(userId: UUID): Member {
        return memberRepository.findById(userId) ?: throw MemberNotFoundException()
    }

    fun contact(memberId: UUID, classification: String, content: String) {
        val member = memberRepository.findById(memberId) ?: throw MemberNotFoundException()
        val slackClient = Slack.getInstance()
        slackClient.send(webHookUrl, "{\"text\" : \"문의사항이 접수되었습니다.\n접수자: ${member.email.value}\n분류: ${classification}\n내용: ${content}\"}")
        return
    }


}