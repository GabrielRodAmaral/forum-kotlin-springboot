package com.amaral.gabriel.forum.config

import com.amaral.gabriel.forum.model.Role
import com.amaral.gabriel.forum.service.UsuarioService
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.util.Date

@Component
class JWTUtil(
    private val usuarioService: UsuarioService
) {

    private val expiration : Long = 60000

    @Value("\${jwt.secrets}")
    private lateinit var secret: String

    // Normalmente o subject é o id, mas nesse caso usaremos o próprio username
    /*
    Nesse caso vamos fazer com que o token expire por motivos de segurança e a forma de criar outro será se autenticando
    novamente, isso não é muito usado no dia a dia e tem formas de gerar outro automaticamente, mas nesse projeto será
    assim
    (Quando for colocar em um projeto real colocar que ele se gere sozinho no momento que for expirado)

    Como estamos setando o expiration para o tempo atual mais um minuto (60000 timemilis) o token expirará após um minuto
    e o usuário terá que se autenticar novamente
     */
    /*
    Sign with é a parte do header do token, como o usuário vai se autenticar, nesse caso usarémos o HS512
    criamos um secret e o passamos para um byteArray para verificar se é igual ao da aplicação
     */
    fun generateToken(username: String, authorities: List<Role>): String? {
        return Jwts.builder()
            .setSubject(username)
            .claim("role", authorities)
            .setExpiration(Date(System.currentTimeMillis() + expiration))
            .signWith(SignatureAlgorithm.HS512, secret.toByteArray())
            .compact()
    }

    fun isValid(jwt: String?): Boolean {
        return try {
            Jwts.parser()
                .setSigningKey(secret.toByteArray())
                .parseClaimsJws(jwt)
            true
        } catch (e: IllegalArgumentException) {
            false
        }
    }

    fun getAuthentication(jwt: String?): Authentication {
        val username = Jwts.parser()
            .setSigningKey(secret.toByteArray())
            .parseClaimsJws(jwt)
            .body.subject
        val user = usuarioService.loadUserByUsername(username)
        return UsernamePasswordAuthenticationToken(username, null, user.authorities)
    }
}