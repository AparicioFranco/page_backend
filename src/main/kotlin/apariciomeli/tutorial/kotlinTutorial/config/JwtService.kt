package apariciomeli.tutorial.kotlinTutorial.config

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.security.Key
import java.util.Date


@Service
class JwtService {

    private val SECRET_KEY: String = "62697631acd9bad6f99074f97f462f7dd95dd6ea1e0a04c9ed3e3ded4134bb5a"


    fun extractUsernameFromToken(token: String): String {
        return extractUsername(token.substring(7))
    }

    fun extractUsername(token: String): String {
        return extractClaim(token, Claims::getSubject)
    }

    fun <T> extractClaim(token: String, claimsResolver: (Claims) -> T): T {
        val claims = extractAllClaims(token)
        return claimsResolver(claims)
    }

    private fun extractAllClaims(token:String): Claims{
        return Jwts
            .parserBuilder()
            .setSigningKey(getSignInKey())
            .build()
            .parseClaimsJws(token)
            .body
    }

    private fun getSignInKey(): Key {
        val keyBytes: ByteArray = Decoders.BASE64.decode(SECRET_KEY)
        return Keys.hmacShaKeyFor(keyBytes)
    }

    fun generateToken(userDetails: UserDetails): String{
        return generateToken(mutableMapOf<String,Any>(), userDetails)
    }

    fun generateToken(
        extraClaims: MutableMap<String, Any>,
        userDetails: UserDetails,
    ): String{
        return Jwts.builder()
            .setClaims(extraClaims)
            .setSubject(userDetails.username)
            .setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(Date(System.currentTimeMillis() + 1000 * 60 * 24))
            .signWith(getSignInKey(), SignatureAlgorithm.HS256)
            .compact();
    }

    fun isTokenValid(token:String, userDetails: UserDetails): Boolean{
        val username:String = extractUsername(token)
        return (username == userDetails.username) && !isTokenExpired(token)
    }

    private fun isTokenExpired(token: String): Boolean {
        return extractExpiration(token).before(Date())
    }

    private fun extractExpiration(token: String): Date {
        return extractClaim(token, Claims::getExpiration)
    }

}
