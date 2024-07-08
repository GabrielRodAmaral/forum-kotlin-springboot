package com.amaral.gabriel.forum.service

import com.amaral.gabriel.forum.dto.AtualizacaoTopicoForm
import com.amaral.gabriel.forum.dto.TopicoForm
import com.amaral.gabriel.forum.dto.TopicoPorCategoriaDto
import com.amaral.gabriel.forum.dto.TopicoView
import com.amaral.gabriel.forum.exception.NotFoundException
import com.amaral.gabriel.forum.mapper.TopicoFormMapper
import com.amaral.gabriel.forum.mapper.TopicoViewMapper
import com.amaral.gabriel.forum.repository.TopicoRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class TopicoService(
    private val repository: TopicoRepository,
    private val topicoViewMapper: TopicoViewMapper,
    private val topicoFormMapper: TopicoFormMapper,
    private val notFoundMessage: String = "Tópico não encontrado"
) {

    fun listar(
        nomeCurso: String?,
        paginacao: Pageable
    ): Page<TopicoView> {
        val topicos = if (nomeCurso == null) {
            repository.findAll(paginacao)
        } else {
            repository.findByCursoNome(nomeCurso, paginacao)
        }
        return topicos.map {
            topicoViewMapper.map(it)
        }
    }

    fun buscarPorId(id: Long): TopicoView {
        val topico =  repository.findById(id).orElseThrow{NotFoundException(notFoundMessage)}
        return topicoViewMapper.map(topico)

    }

    fun cadastrar(topico: TopicoForm): TopicoView {
        val t = topicoFormMapper.map(topico)
        repository.save(t)
        return topicoViewMapper.map(t)
    }

    fun atualizar(t: AtualizacaoTopicoForm): TopicoView{
        val topico = repository.findById(t.id).orElseThrow{ NotFoundException(notFoundMessage) }
        topico.titulo = t.titulo
        topico.mensagem = t.mensagem
        return topicoViewMapper.map(topico)
    }

    fun deletar(id: Long) {
        repository.deleteById(id)
    }

    fun relatorio(): List<TopicoPorCategoriaDto> {
        return repository.relatorio()
    }

}