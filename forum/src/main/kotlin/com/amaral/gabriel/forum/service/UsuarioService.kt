package com.amaral.gabriel.forum.service

import com.amaral.gabriel.forum.exception.NotFoundException
import com.amaral.gabriel.forum.model.Usuario
import com.amaral.gabriel.forum.repository.UsuarioRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UsuarioService(
    private val repository: UsuarioRepository
): UserDetailsService {

    fun buscarPorId(id: Long): Usuario {
        return repository.findByIdOrNull(id) ?: throw NotFoundException("Usuário não encontrado")
    }

    override fun loadUserByUsername(username: String?): UserDetails {
        val usuario = repository.findByEmail(username) ?: throw RuntimeException()
        return UserDetail(usuario)
    }

}
