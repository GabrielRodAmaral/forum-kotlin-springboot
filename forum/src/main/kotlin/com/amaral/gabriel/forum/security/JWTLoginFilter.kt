package com.amaral.gabriel.forum.security

import com.amaral.gabriel.forum.config.JWTUtil
import com.amaral.gabriel.forum.model.Credentials
import com.amaral.gabriel.forum.service.UserDetail
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

class JWTLoginFilter(
    private val authManager: AuthenticationManager,
    private val jwtUtil: JWTUtil
) : UsernamePasswordAuthenticationFilter() {

    /*
    Credentials foi criado no model com as propriedades username e password, ao chamar o authenticate ele vai utilizar
    toda a estrutura que tinha sido criada anteriormente com o spring security, buscando o usuário no banco de dados
    pelo user name, o autenticando, depois se der successful vai cair na funçaõ abaixo
     */
    override fun attemptAuthentication(request: HttpServletRequest?, response: HttpServletResponse?): Authentication {
        val(username, password) = ObjectMapper().readValue(request?.inputStream, Credentials::class.java)
        val token = UsernamePasswordAuthenticationToken(username, password)
        return authManager.authenticate(token)
    }

    /*
    principal é um userDetail pelas configurações feitas anteriormente
    adicionamos o token ao cabeçalho da resposta
    token por padrão tem um tipo Bearer
     */
    override fun successfulAuthentication(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        chain: FilterChain?,
        authResult: Authentication?
    ) {
        val user = (authResult?.principal as UserDetail)
        val token = jwtUtil.generateToken(user.username, user.authorities)
        response?.addHeader("Authorization", "Bearer $token")
    }
}
