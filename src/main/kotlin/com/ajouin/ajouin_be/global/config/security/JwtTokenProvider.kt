package com.ajouin.ajouin_be.global.config.security

import com.ajouin.ajouin_be.domain.member.domain.AuthUser
import com.ajouin.ajouin_be.global.config.security.exception.ExpiredTokenException
import com.ajouin.ajouin_be.global.config.security.exception.InvalidTokenException
import io.jsonwebtoken.*
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import io.jsonwebtoken.security.SecurityException
import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.util.*

@Component
@Slf4j
class JwtTokenProvider {
    private val log = LoggerFactory.getLogger(this.javaClass)!!

    @Value("\${jwt.secret}")
    lateinit var secretKey: String

    @Value("\${jwt.expiration}")
    var expirationTime: Long = 2592000000L

    private val key by lazy { Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey))}

    fun createToken(authentication: Authentication): TokenInfo {
        val authorities: String = authentication.authorities
            .joinToString(",", transform = GrantedAuthority::getAuthority)

        val now = Date()
        val accessExpiration = Date(now.time + expirationTime)

        //claim에 userId 추가 필요
        //이외에도 claim에 추가할 정보가 있으면 추가
        val accessToken = Jwts.builder()
            .setSubject(authentication.name)
            .claim("auth", authorities)
            .claim("userId", (authentication.principal as AuthUser).id)
            .setIssuedAt(now)
            .setExpiration(accessExpiration)
            .signWith(key, SignatureAlgorithm.HS256)
            .compact()

        return TokenInfo("Bearer", accessToken)
    }

    //Token 정보 추출
    fun getAuthentication(token: String): Authentication {
        val claims: Claims = getClaims(token)

        val auth = claims["auth"] ?: throw InvalidTokenException()
        val userId = claims["userId"] ?: throw InvalidTokenException()

        log.info("userId: $userId")

        val authorities: Collection<GrantedAuthority> = (auth as String)
            .split(",")
            .map { SimpleGrantedAuthority(it) }

        val principal: UserDetails = AuthUser(
            id = UUID.fromString(userId as String),
            email = claims.subject,
            password = "",
            authorities = authorities
        )

        return UsernamePasswordAuthenticationToken(principal, "", authorities)
    }

    fun validateToken(token: String): Boolean {
        try {
            getClaims(token)
            return true
        } catch (e: Exception) {
            when (e) {
                is SecurityException -> throw SecurityException()
                is MalformedJwtException -> throw InvalidTokenException()
                is ExpiredJwtException -> throw ExpiredTokenException()
                is UnsupportedJwtException -> throw InvalidTokenException()
                is IllegalArgumentException -> throw InvalidTokenException()
                else -> throw SecurityException()
            }
        }
    }

    private fun getClaims(token: String): Claims {
        return Jwts
            .parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .body
    }
}