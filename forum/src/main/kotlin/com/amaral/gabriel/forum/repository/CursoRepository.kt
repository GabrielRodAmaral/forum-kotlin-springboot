package com.amaral.gabriel.forum.repository

import com.amaral.gabriel.forum.model.Curso
import org.springframework.data.jpa.repository.JpaRepository

interface CursoRepository: JpaRepository<Curso, Long> {
}