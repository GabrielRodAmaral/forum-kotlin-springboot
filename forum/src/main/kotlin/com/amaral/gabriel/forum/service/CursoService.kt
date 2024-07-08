package com.amaral.gabriel.forum.service

import com.amaral.gabriel.forum.dto.CursoForm
import com.amaral.gabriel.forum.exception.NotFoundException
import com.amaral.gabriel.forum.mapper.CursoFormMapper
import com.amaral.gabriel.forum.model.Curso
import com.amaral.gabriel.forum.repository.CursoRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class CursoService(
    private val repository: CursoRepository,
    private val cursoFormMapper: CursoFormMapper
) {

    fun buscarPorId(id: Long): Curso {
        return repository.findByIdOrNull(id) ?: throw NotFoundException("Curso não encontrado")
    }

        fun save(curso: CursoForm): Curso {
            val c = cursoFormMapper.map(curso)
            repository.save(c)
            return c
        }
}
