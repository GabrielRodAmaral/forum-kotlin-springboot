package com.amaral.gabriel.forum.model

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*

@Entity
data class Usuario(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val nome: String,
    val email: String,
    val password: String,

//    Fetch do tipo Eager faz com que no momento de buscar o usuário sejam carregadas todas as roles do mesmo
//    O tipo lazy faria ao contrário
//    JsonIgnore para quando mostrar um usuário não mostrar a lista de roles para evitar erro de out of memory
//    devido a um loop que pode acontecer na aplicação
    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "usuario_role",
        joinColumns = [JoinColumn(name = "usuario_id")],
        inverseJoinColumns = [JoinColumn(name = "role_id")]
    ) // Mostra de onde será o join
    val role: List<Role> = mutableListOf()
)
