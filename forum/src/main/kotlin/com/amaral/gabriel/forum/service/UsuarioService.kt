package com.amaral.gabriel.forum.service

import com.amaral.gabriel.forum.model.Usuario
import org.springframework.stereotype.Service

@Service
class UsuarioService(
    private var usuarios: List<Usuario>
) {

    init {
        val usuario = Usuario(
            id = 1,
            nome = "Amaral",
            "amaralnator@kotlin.com"
        )

        usuarios = listOf(usuario)
    }

    fun buscarPorId(id: Long): Usuario {
        return usuarios.first {it.id == id}
    }

}
