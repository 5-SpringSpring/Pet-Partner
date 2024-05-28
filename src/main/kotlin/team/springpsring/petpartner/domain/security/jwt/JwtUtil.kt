package team.springpsring.petpartner.domain.security.jwt

import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.time.Duration
import java.time.Instant
import java.util.Date
import javax.crypto.SecretKey

@Component
class JwtUtil {
    private val ISSUER="team.sparta.com"
    private val SECRET="5FZV1z9KHf8sv3bXxG2d9kL8Yd1MhX5PsmL4uQJH9Zge"
    private val ACCESS_TOKEN_EXPIRATION_HOUR:Long=168

    fun validateToken(token:String): String{
        val key = Keys.hmacShaKeyFor(SECRET.toByteArray(StandardCharsets.UTF_8))
        try {
            val claims = Jwts.parser().verifyWith(key).build().parseSignedClaims(token)
            return claims.payload["loginId"].toString()
        } catch (e:Exception){
            throw JwtException("Invalid JWT token")
        }
    }

    fun generateAccessToken(subject:String, loginId: String): String {
        return generateToken(subject,loginId,Duration.ofHours(ACCESS_TOKEN_EXPIRATION_HOUR))
    }

    private fun generateToken(subject:String, loginId: String, expirationPeriod:Duration?): String {
        val claims=Jwts.claims()
            .add(mapOf("loginId" to loginId))
            .build()

        val key: SecretKey =Keys.hmacShaKeyFor(SECRET.toByteArray(StandardCharsets.UTF_8))
        val now=Instant.now()

        return Jwts.builder()
            .subject(subject)
            .issuer(ISSUER)
            .issuedAt(Date.from(now))
            .expiration(Date.from(now.plus(expirationPeriod)))
            .claims(claims)
            .signWith(key)
            .compact()
    }
}