package com.amaral.gabriel.forum.service

import com.amaral.gabriel.forum.dto.AtualizacaoTopicoForm
import com.amaral.gabriel.forum.dto.TopicoForm
import com.amaral.gabriel.forum.dto.TopicoView
import com.amaral.gabriel.forum.exception.NotFoundException
import com.amaral.gabriel.forum.mapper.TopicoFormMapper
import com.amaral.gabriel.forum.mapper.TopicoViewMapper
import com.amaral.gabriel.forum.model.Topico
import org.springframework.stereotype.Service

@Service
class TopicoService(
    private var topicos: List<Topico> = emptyList(),
    private val topicoViewMapper: TopicoViewMapper,
    private val topicoFormMapper: TopicoFormMapper,
    private val notFoundMessage: String = "Tópico não encontrado"
) {

    fun listar(): List<TopicoView> {
        return topicos.map {
            topicoViewMapper.map(it)
        }
    }

    fun buscarPorId(id: Long): TopicoView {
        return topicos.first { it.id == id }.let {
            topicoViewMapper.map(it)
        }

    }

    fun cadastrar(topico: TopicoForm): TopicoView {
        val t = topicoFormMapper.map(topico)
        t.id = topicos.size.toLong() + 1
        topicos = topicos.plus(t)

        return topicoViewMapper.map(t)
    }

    fun atualizar(t: AtualizacaoTopicoForm): TopicoView{
        val topico = topicos.firstOrNull { it.id == t.id } ?: throw NotFoundException(notFoundMessage)
        val topicoAtt = Topico(
            id = t.id,
            titulo = t.titulo,
            mensagem = t.mensagem,
            autor = topico.autor,
            curso = topico.curso,
            respostas = topico.respostas,
            status = topico.status,
            dataCriacao = topico.dataCriacao
        )
        topicos = topicos.minus(topico).plus(topicoAtt)
        return topicoViewMapper.map(topicoAtt)
    }

    fun deletar(id: Long) {
        val topico = topicos.firstOrNull { it.id == id } ?: throw NotFoundException(notFoundMessage)
        topicos = topicos.minus(topico)
    }

}