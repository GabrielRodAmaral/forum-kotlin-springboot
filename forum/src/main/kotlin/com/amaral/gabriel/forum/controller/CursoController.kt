package com.amaral.gabriel.forum.controller

import com.amaral.gabriel.forum.dto.CursoForm
import com.amaral.gabriel.forum.model.Curso
import com.amaral.gabriel.forum.service.CursoService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.util.UriBuilder
import org.springframework.web.util.UriComponentsBuilder

@RestController
@RequestMapping("/cursos")
class CursoController(
    private val service: CursoService
) {

    @PostMapping
    fun save(
        @RequestBody @Valid curso: CursoForm,
        uriBuilder: UriComponentsBuilder
    ): ResponseEntity<Curso> {
        val saved = service.save(curso)
        val uri = uriBuilder.path("/cursos/${saved.id}").build().toUri()
        return ResponseEntity.created(uri).body(saved)
    }
}