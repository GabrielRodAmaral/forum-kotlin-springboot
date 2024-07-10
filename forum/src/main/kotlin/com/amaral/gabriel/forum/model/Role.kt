package com.amaral.gabriel.forum.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import org.springframework.security.core.GrantedAuthority

@Entity
data class Role(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long,
    private val nome: String
) : GrantedAuthority {
    /*
    A interface GrantedAuthority com o método abaixo define o que será retornado quando for requisitada a Authority
    do usuário pela role, fazendo nesse caso retornar nome, para ser usado pela UserDetail que vai utilizar no método
    getAuthorities
     */
    override fun getAuthority() = nome

}
