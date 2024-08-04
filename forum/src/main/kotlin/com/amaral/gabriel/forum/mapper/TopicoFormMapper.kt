package com.amaral.gabriel.forum.mapper

import com.amaral.gabriel.forum.dto.TopicoForm
import com.amaral.gabriel.forum.model.Topico
import com.amaral.gabriel.forum.service.CursoService
import com.amaral.gabriel.forum.service.UsuarioService
import org.springframework.stereotype.Component

@Component
class TopicoFormMapper(
    private val cursoService: CursoService,
    private val usuarioService: UsuarioService
): Mapper<TopicoForm, Topico> {

    override fun map(t: TopicoForm): Topico {
        return Topico(
            titulo = t.titulo,
            mensagem = t.mensagem,
            curso = cursoService.buscarPorId(t.idCurso),
            autor = usuarioService.buscarPorId(t.idAutor),
        )
    }

}