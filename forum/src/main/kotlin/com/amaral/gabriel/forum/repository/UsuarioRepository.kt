package com.amaral.gabriel.forum.repository

import com.amaral.gabriel.forum.model.Usuario
import org.springframework.data.jpa.repository.JpaRepository

interface UsuarioRepository: JpaRepository<Usuario, Long> {
    fun findByEmail(username: String?): Usuario?
}