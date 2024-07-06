package com.amaral.gabriel.forum.dto

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class TopicoForm(
    @field:NotEmpty(message = "Título não pode estar em branco")
    @field:Size(min = 5, max = 100, message = "Título deve ter entre 5 e 100 caracteres")
    val titulo: String,
    @field:NotEmpty(message = "Mensagem não pode estar em branco") val mensagem: String,
    @field:NotNull(message = "Id do curso não pode ser nulo") val idCurso: Long,
    @field:NotNull(message = "Id do autor não pode ser nulo") val idAutor: Long
)
