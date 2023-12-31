package com.ajouin.ajouin_be.global.error

import com.ajouin.ajouin_be.global.error.exception.BusinessException
import com.ajouin.ajouin_be.global.error.exception.ErrorCode
import com.slack.api.Slack
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import java.net.BindException
import java.net.SocketTimeoutException

@RestControllerAdvice
class ExceptionHandler {

    @ExceptionHandler(BusinessException::class)
    fun handleBusinessException(e: BusinessException): ResponseEntity<ErrorCode> {
        return ResponseEntity.badRequest().body(e.errorCode)
    }
    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseEntity<ErrorCode> {
        val errorResponse = ErrorCode.INTERNAL_SERVER_ERROR
        return ResponseEntity.status(errorResponse.status).body(errorResponse)
    }
    @ExceptionHandler(AccessDeniedException::class)
    fun handleAccessDeniedException(e: AccessDeniedException): ResponseEntity<ErrorCode> {
        val errorResponse = ErrorCode.HANDLE_ACCESS_DENIED
        return ResponseEntity.status(errorResponse.status).body(errorResponse)
    }
    @ExceptionHandler(HttpRequestMethodNotSupportedException::class)
    fun handleHttpRequestMethodNotSupportedException(e: HttpRequestMethodNotSupportedException): ResponseEntity<ErrorCode> {
        val errorResponse = ErrorCode.METHOD_NOT_ALLOWED
        return ResponseEntity.status(errorResponse.status).body(errorResponse)
    }
    @ExceptionHandler(MethodArgumentTypeMismatchException::class)
    fun handleMethodArgumentTypeMismatchException(e: MethodArgumentTypeMismatchException): ResponseEntity<ErrorCode> {
        val errorResponse = ErrorCode.INVALID_TYPE_VALUE
        return ResponseEntity.status(errorResponse.status).body(errorResponse)
    }

    @ExceptionHandler(BadCredentialsException::class)
    fun handleBadCredentialsException(e: BadCredentialsException): ResponseEntity<ErrorCode> {
        val errorResponse = ErrorCode.LOGIN_INPUT_INVALID
        return ResponseEntity.status(errorResponse.status).body(errorResponse)
    }

    @ExceptionHandler(BindException::class)
    fun handleBindException(e: BindException): ResponseEntity<ErrorCode> {
        val errorResponse = ErrorCode.INVALID_TYPE_VALUE
        return ResponseEntity.status(errorResponse.status).body(errorResponse)
    }
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(e: MethodArgumentNotValidException): ResponseEntity<ErrorCode> {
        val errorResponse = ErrorCode.INVALID_INPUT_VALUE
        return ResponseEntity.status(errorResponse.status).body(errorResponse)
    }

}