package com.amaral.gabriel.forum.mapper

import com.amaral.gabriel.forum.dto.CursoForm
import com.amaral.gabriel.forum.model.Curso
import org.springframework.cache.annotation.EnableCaching
import org.springframework.stereotype.Component

@Component
class CursoFormMapper: Mapper<CursoForm, Curso> {
    override fun map(t: CursoForm): Curso {
        return Curso(
            nome = t.nome,
            categoria = t.categoria
        )
    }
}