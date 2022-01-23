package com.axis.ecommerce.security

import com.axis.ecommerce.service.UserDetailsImpl
import io.jsonwebtoken.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.util.*

@Component
class JwtUtils {
    val logger:Logger=LoggerFactory.getLogger(JwtUtils::class.java)
    @Value("\${jwt.secret}")
    private val SECRET_KEY: String? = null

    @Value("\${jwt.jwtExp}")
    private val JWT_EXP = 0
    fun generateJwtToken(authentication: Authentication): String? {
        val userPrincipal: UserDetailsImpl = authentication.principal as UserDetailsImpl
        return Jwts.builder()
            .setSubject(userPrincipal.getUsername())
            .setIssuedAt(Date())
            .setExpiration(Date(Date().time + JWT_EXP))
            .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
            .compact()
    }
    fun getUserNameFromJwtToken(token: String?): String? {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getSubject()
    }
    fun validateJwtToken(authToken: String?): Boolean {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(authToken)
            return true
        } catch (e: SignatureException) {
            logger.error("Invalid JWT signature: {}", e.message)
        } catch (e: MalformedJwtException) {
            logger.error("Invalid JWT token: {}", e.message)
        } catch (e: ExpiredJwtException) {
            logger.error("JWT token is expired: {}", e.message)
        } catch (e: UnsupportedJwtException) {
            logger.error("JWT token is unsupported: {}", e.message)
        } catch (e: IllegalArgumentException) {
            logger.error("JWT claims string is empty: {}", e.message)
        }
        return false
    }
}