package com.amaral.gabriel.forum.model

object TopicoTest {
    fun build() = Topico(
        id = 1,
        titulo = "Android",
        mensagem = "Compose com koin",
        curso = CursoTest.build(),
        autor = UsuarioTest.build()
    )
}