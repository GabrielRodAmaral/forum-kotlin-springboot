package com.amaral.gabriel.forum.config

import com.amaral.gabriel.forum.security.JWTAuthenticationFilter
import com.amaral.gabriel.forum.security.JWTLoginFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.filter.OncePerRequestFilter

// classe para configurações de segurança como definir o que pode ou não ser acessado sem autenticação

@Configuration
@EnableWebSecurity
class SecurityConfiguration(
    private val configuration: AuthenticationConfiguration,
    private val userDetailService: UserDetailsService,
    private val jwtUtil: JWTUtil
) {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        return http
            .csrf { it.disable() } //csrf é uma autenticação para impedir que alguém malicioso use um token válido de outro usuário para fazer chamadas malicioasas, em prod é bom, aqui não precisa
            .authorizeHttpRequests {
                // Definindo aqui que para chamar /topicos o usuário deve possuir a autorização (role) de LEITURA E ESCRITA
                it
                    .requestMatchers(HttpMethod.GET, "/topicos").hasAuthority("LEITURA_E_ESCRITA")
                    .requestMatchers(HttpMethod.POST, "/login")?.permitAll()?.anyRequest()?.authenticated()
            }
            .addFilterBefore(
                JWTLoginFilter(authManager = configuration.authenticationManager, jwtUtil = jwtUtil),
                UsernamePasswordAuthenticationFilter().javaClass
            )
            .addFilterBefore(JWTAuthenticationFilter(jwtUtil), UsernamePasswordAuthenticationFilter().javaClass)
            .sessionManagement {
                it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }.build()
    }

    @Bean
    fun encoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun configure(): DaoAuthenticationProvider {
        val authProvider = DaoAuthenticationProvider()
        authProvider.setUserDetailsService(userDetailService)
        authProvider.setPasswordEncoder(encoder())
        return authProvider
    }
}