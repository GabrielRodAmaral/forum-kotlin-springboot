package com.amaral.gabriel.forum.controller

import com.amaral.gabriel.forum.dto.AtualizacaoTopicoForm
import com.amaral.gabriel.forum.dto.TopicoForm
import com.amaral.gabriel.forum.dto.TopicoPorCategoriaDto
import com.amaral.gabriel.forum.dto.TopicoView
import com.amaral.gabriel.forum.service.TopicoService
import jakarta.validation.Valid
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder

@RestController
@RequestMapping("/topicos")
class TopicoController(
    private val service: TopicoService
) {

    // Form é utilizada quando o usuário envia um json para a aplicação e view é um dto de saida, para exibir ao usuário
    // Os dois são Dtos, esses nomes servem apenas para organização

    //    Essa função lista todos os tópicos, mas foi adicionado um requestparam opcional caso o usuário queira filtrar por
//    curso, required = false faz ser opcional

    /*
    CACHE
    Para fins didáticos vou usar o cache na listagem de tópicos, porém isso não aconteceria em um projeto para prod real
    porque cache deve ser utilizado em consultas que não costumam ocorrer mudanças no retorno, o objetivo é que um dado
    que é buscado com frequência e não muda praticamente nunca seja armazenado em cache para que seja mais rápido
    busca-lo sem fazer consultas no banco sempre
    usar o Cacheable do spring e não do jakarta
     */
    @GetMapping
    @Cacheable("topicos")
    fun listar(
        @RequestParam(required = false) nomeCurso: String?,
        @PageableDefault(sort = ["dataCriacao"], direction = Sort.Direction.DESC) paginacao: Pageable
    ): Page<TopicoView> {
        return service.listar(nomeCurso, paginacao)
    }

    @GetMapping("/{id}")
    fun buscarPorId(@PathVariable id: Long): TopicoView {
        return service.buscarPorId(id)
    }

    // nesse eu coloquei o value = [], só para lembrar que o CacheEvict recebe um array, e ele poderia limpar vários
    // caches
    @PostMapping
    @Transactional
    @CacheEvict(value = ["topicos"], allEntries = true)
    fun cadastrar(
        @RequestBody @Valid topico: TopicoForm,
        uriBuilder: UriComponentsBuilder
    ): ResponseEntity<TopicoView> {
        val topicoView = service.cadastrar(topico)
        val uri = uriBuilder.path("/topicos/${topicoView.id}").build().toUri()
        return ResponseEntity.created(uri).body(topicoView)
    }

    @PutMapping
    @Transactional
    @CacheEvict("topicos", allEntries = true)
    fun atualizar(@RequestBody @Valid topico: AtualizacaoTopicoForm): ResponseEntity<TopicoView> {
        val att = service.atualizar(topico)
        return ResponseEntity.ok(att)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    @CacheEvict("topicos", allEntries = true)
    fun deletar(@PathVariable id: Long) {
        service.deletar(id)
    }

    @GetMapping("/relatorio")
    fun relatorio(): List<TopicoPorCategoriaDto> {
        return service.relatorio()
    }
}