package com.axis.ecommerce.security

import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
@Component
class AuthEntryPoint:AuthenticationEntryPoint {
    val logger:Logger=LoggerFactory.getLogger(AuthEntryPoint::class.java)
    override fun commence(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authException: AuthenticationException?
    ) {
        response!!.contentType = MediaType.APPLICATION_JSON_VALUE
        response!!.status = HttpServletResponse.SC_UNAUTHORIZED
        val body= mutableMapOf<String,Any>()

        body.put("status", HttpServletResponse.SC_UNAUTHORIZED)
        body.put("error", "Unauthorized")
        body.put("message", authException!!.message!!)
        body.put("path", request!!.servletPath)
        val mapper = ObjectMapper()
        mapper.writeValue(response.outputStream, body)
    }
}