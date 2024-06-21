package apariciomeli.tutorial.kotlinTutorial.config

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter(
    private val jwtService: JwtService,
    private val userDetailsService: UserDetailsService,
): OncePerRequestFilter() {



    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
//        response.setHeader("Access-Control-Allow-Origin", "*"); // * = all domainName
//        response.setHeader("Access-Control-Allow-Credentials", "true"); // allow CrossDomain to use Origin Domain
//        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT");
//        response.setHeader("Access-Control-Max-Age", "3600"); // Preflight cache duration in browser
//        response.setHeader("Access-Control-Allow-Headers", "*"); // all header
        if (request.servletPath.contains("/api/v1/auth")) {
            filterChain.doFilter(request, response)
            return
        }
        val authHeader = request.getHeader("Authorization")
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response)
            return
        }
        val jwt: String = authHeader.substring(7)
        val userEmail: String = jwtService.extractUsername(jwt)
        if (userEmail !== null && SecurityContextHolder.getContext().authentication == null){
            val userDetails: UserDetails = userDetailsService.loadUserByUsername(userEmail)
            if (jwtService.isTokenValid(jwt,userDetails)){
                val authToken = UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.authorities
                )
                authToken.details = WebAuthenticationDetailsSource().buildDetails(request)
                SecurityContextHolder.getContext().authentication = authToken
            }
        }
        filterChain.doFilter(request,response)
    }
}