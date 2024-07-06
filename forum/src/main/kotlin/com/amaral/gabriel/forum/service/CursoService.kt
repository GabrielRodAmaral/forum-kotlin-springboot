package com.amaral.gabriel.forum.service

import com.amaral.gabriel.forum.model.Curso
import org.springframework.stereotype.Service

@Service
class CursoService(
    private var cursos: List<Curso>
) {

    init {
        val curso = Curso(
            id = 1,
            nome = "Spring boot com kotlin",
            categoria = "Backend"
        )

        cursos = listOf(curso)
    }

    fun buscarPorId(id: Long): Curso {
        return cursos.first {it.id == id}
    }
}
