package com.amaral.gabriel.forum.service

import com.amaral.gabriel.forum.model.Usuario
import org.springframework.security.core.userdetails.UserDetails

class UserDetail(
    private val usuario: Usuario
): UserDetails {
    override fun getAuthorities() = usuario.role

    override fun getPassword() = usuario.password

    override fun getUsername() = usuario.email
}