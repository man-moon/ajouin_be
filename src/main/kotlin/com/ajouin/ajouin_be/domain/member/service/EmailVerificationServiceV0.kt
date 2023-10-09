package com.ajouin.ajouin_be.domain.member.service

import com.ajouin.ajouin_be.domain.member.domain.EmailVerification
import com.ajouin.ajouin_be.domain.member.dto.request.CodeVerificationRequest
import com.ajouin.ajouin_be.domain.member.dto.request.EmailVerificationRequest
import com.ajouin.ajouin_be.domain.member.dto.request.PasswordResetRequest
import com.ajouin.ajouin_be.domain.member.exception.EmailDuplicateException
import com.ajouin.ajouin_be.domain.member.repository.EmailVerificationRepository
import com.ajouin.ajouin_be.domain.member.repository.MemberRepository
import com.ajouin.ajouin_be.global.config.EmailConfig
import com.ajouin.ajouin_be.global.error.exception.BusinessException
import com.ajouin.ajouin_be.global.error.exception.ErrorCode
import com.ajouin.ajouin_be.global.error.exception.InvalidValueException
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class EmailVerificationServiceV0(
    private val memberRepository: MemberRepository,
    private val codeGenerator: CodeGenerator,
    private val emailVerificationRepository: EmailVerificationRepository,
    private val mailSender: JavaMailSender,
    private val emailConfig: EmailConfig,
): EmailVerificationService {
    @Transactional
    override fun sendVerificationEmail(
        emailVerificationRequest: EmailVerificationRequest
    ): EmailVerification {

        verifyEmailOrThrowException(emailVerificationRequest)

        val verificationCode = codeGenerator.generate()
        val savedVerification = emailVerificationRepository.save(
            EmailVerification(
                email = emailVerificationRequest.email,
                code = verificationCode,
            )
        )

        val message = buildEmailMessage(emailVerificationRequest, verificationCode)
        sendEmail(message)

        return savedVerification
    }

    @Transactional
    override fun verifyCode(codeVerificationRequest: CodeVerificationRequest): Boolean {
        val emailVerification = emailVerificationRepository.findById(codeVerificationRequest.id)
        verifyCodeOrThrowException(emailVerification, codeVerificationRequest)

        val verification = emailVerificationRepository.findById(codeVerificationRequest.id)
            ?: throw InvalidValueException()

        verification.isVerified = true
        emailVerificationRepository.save(verification)

        return true;
    }

    @Transactional
    override fun sendVerificationEmailForResetPassword(
        emailVerificationRequest: EmailVerificationRequest
    ): EmailVerification {

        //이미 가입된 이메일인지 확인
        val isExistingMember: Boolean = memberRepository.existsByEmail(emailVerificationRequest.email)
        if (!isExistingMember) {
            throw BusinessException(ErrorCode.EMAIL_NOT_EXIST)
        }

        val verificationCode = codeGenerator.generate()
        val savedVerification = emailVerificationRepository.save(
            EmailVerification(
                email = emailVerificationRequest.email,
                code = verificationCode,
            )
        )

        val message = buildEmailMessage(emailVerificationRequest, verificationCode)
        sendEmail(message)

        return savedVerification
    }

    private fun verifyCodeOrThrowException(
        emailVerification: EmailVerification?,
        codeVerificationRequest: CodeVerificationRequest
    ) {
        if (emailVerification?.code != codeVerificationRequest.code) {
            throw InvalidValueException(ErrorCode.CODE_INVALID)
        }
        if (emailVerification.isExpired()) {
            throw BusinessException(ErrorCode.TIMEOUT)
        }
    }

    private fun verifyEmailOrThrowException(emailVerificationRequest: EmailVerificationRequest) {
        val isExistingMember: Boolean = memberRepository.existsByEmail(emailVerificationRequest.email)
        if (isExistingMember) {
            throw EmailDuplicateException()
        }

        if (emailVerificationRequest.email.isNotAjouUnivEmail()) {
            throw InvalidValueException(ErrorCode.EMAIL_INVALID)
        }
    }

    private fun buildEmailMessage(
        emailVerificationRequest: EmailVerificationRequest,
        verificationCode: String
    ): SimpleMailMessage {
        val message = SimpleMailMessage()
        message.setTo(emailVerificationRequest.email.value)
        message.subject = emailConfig.subject
        message.text = String.format(emailConfig.text, verificationCode)
        return message
    }

    private fun sendEmail(message: SimpleMailMessage) {
        try {
            mailSender.send(message)
        } catch (e: Exception) {
            throw BusinessException(ErrorCode.INTERNAL_SERVER_ERROR)
        }
    }
}