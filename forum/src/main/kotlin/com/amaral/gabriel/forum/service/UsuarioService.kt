package com.amaral.gabriel.forum.service

import com.amaral.gabriel.forum.exception.NotFoundException
import com.amaral.gabriel.forum.model.Usuario
import com.amaral.gabriel.forum.repository.UsuarioRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class UsuarioService(
    private val repository: UsuarioRepository
) {

    fun buscarPorId(id: Long): Usuario {
        return repository.findByIdOrNull(id) ?: throw NotFoundException("Usuário não encontrado")
    }

}
