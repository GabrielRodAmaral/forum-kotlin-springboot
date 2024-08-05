package com.amaral.gabriel.forum.service

import com.amaral.gabriel.forum.exception.NotFoundException
import com.amaral.gabriel.forum.mapper.TopicoFormMapper
import com.amaral.gabriel.forum.mapper.TopicoViewMapper
import com.amaral.gabriel.forum.model.TopicoTest
import com.amaral.gabriel.forum.model.TopicoViewTest
import com.amaral.gabriel.forum.repository.TopicoRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import java.util.*

class TopicoServiceTest {

    val topicos = PageImpl(listOf(TopicoTest.build()))

    val paginacao: Pageable = mockk()

    // Além de mockar classes como por exemplo repository com mockk() é possível simular ações, como por exemplo o que
//    Deve acontecer quando uma função do mesmo for chamada
    val topicoRepository: TopicoRepository = mockk {
        every { findByCursoNome(any(), any()) } returns topicos
        every { findAll(paginacao) } returns topicos
    }

    val topicoViewMapper: TopicoViewMapper = mockk()
    val topicoFormMapper: TopicoFormMapper = mockk()

    val topicoService = TopicoService(
        topicoRepository,
        topicoViewMapper,
        topicoFormMapper
    )

    @Test
    fun `Deve listar tópicos a partir do nome do curso`() {
        every {
            topicoViewMapper.map(any())
        } returns TopicoViewTest.build()

        topicoService.listar("Android com kotlin", paginacao)

        // Verificar se foi chamado exatamente uma vez
        verify(exactly = 1) { topicoRepository.findByCursoNome(any(), any()) }
        verify(exactly = 1) { topicoViewMapper.map(any()) }
        verify(exactly = 0) { topicoRepository.findAll(paginacao) }
    }

    @Test
    fun `Deve listar todos os tópicos caso não seja passado um nome de curso`() {
        every {
            topicoViewMapper.map(any())
        } returns TopicoViewTest.build()

        topicoService.listar(null, paginacao)

        // Verificar se foi chamado exatamente uma vez
        verify(exactly = 0) { topicoRepository.findByCursoNome(any(), any()) }
        verify(exactly = 1) { topicoViewMapper.map(any()) }
        verify(exactly = 1) { topicoRepository.findAll(paginacao) }
    }

    @Test
    fun `Deve lançar NotFoundException quando o Topico não for encontrado`() {
        every {
            topicoRepository.findById(any())
        } returns Optional.empty()

        val atual = assertThrows<NotFoundException> {
            topicoService.buscarPorId(9)
        }

        assertThat(atual.message).isEqualTo("Tópico não encontrado")
    }
}