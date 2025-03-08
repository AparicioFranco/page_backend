package apariciomeli.tutorial.kotlinTutorial.config

import apariciomeli.tutorial.kotlinTutorial.model.Role
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource


@Configuration
@EnableWebSecurity
class SecurityConfiguration(
    val jwtAuthFilter: JwtAuthenticationFilter,
    val authenticationProvider: AuthenticationProvider,
) {


    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf{ csrf ->
                csrf.disable()
            }
            .authorizeHttpRequests { authorizeHttpRequests ->
                authorizeHttpRequests
                    .requestMatchers("/api/auth/public/**").permitAll()
                    .requestMatchers("/webhook").permitAll()
                    .requestMatchers("/api/auth/private/**").hasAuthority(Role.ADMIN.name)
                    .requestMatchers("/api/comment/public/**").hasAnyAuthority(Role.USER.name, Role.ADMIN.name)
                    .requestMatchers("/api/comment/private/**").hasAuthority(Role.ADMIN.name)
                    .requestMatchers("/api/course/public/**").hasAnyAuthority(Role.USER.name, Role.ADMIN.name)
                    .requestMatchers("/api/course/private/**").hasAuthority(Role.ADMIN.name)
                    .requestMatchers("/api/user/public/**").hasAnyAuthority(Role.USER.name, Role.ADMIN.name)
                    .requestMatchers("/api/user/private/**").hasAuthority(Role.ADMIN.name)
                    .requestMatchers("/api/module/public/**").hasAnyAuthority(Role.USER.name, Role.ADMIN.name)
                    .requestMatchers("/api/module/private/**").hasAuthority(Role.ADMIN.name)
                    .requestMatchers("/api/module-data/public/**").hasAnyAuthority(Role.USER.name, Role.ADMIN.name)
                    .requestMatchers("/api/module-data/private/**").hasAuthority(Role.ADMIN.name)
                    .anyRequest()
                    .authenticated()
            }
            .cors {  cors ->
                cors.configurationSource(corsConfigurationSource())
            }
            .sessionManagement { SessionCreationPolicy.STATELESS }
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter::class.java)
        return http.build()
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration()
        configuration.allowedOrigins = listOf("*")
        configuration.allowedMethods = listOf("*")
        configuration.allowedHeaders = listOf("*")
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }
}