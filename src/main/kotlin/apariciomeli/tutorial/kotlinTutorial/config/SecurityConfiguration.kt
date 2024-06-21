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


@Configuration
@EnableWebSecurity
class SecurityConfiguration(
    val jwtAuthFilter: JwtAuthenticationFilter,
    val authenticationProvider: AuthenticationProvider,
) {

    val PERMIT_ALL_COMMENT = arrayOf(
        "/api/comment/add",
        "/api/comment/own/"
    )

    val PERMIT_ALL_COURSE: Array<String> = arrayOf(
        "/api/course/id/",
    )

    val PERMIT_ALL_USER: Array<String> = arrayOf(
        "/api/user/own/course",
        "/api/user/module/read",
        "/api/user/module/completed"
    )

    val PERMIT_ALL_MODULE: Array<String> = arrayOf(
        "/api/module/course/available/",
    )

    val PERMIT_ALL_MODULE_DATA: Array<String> = arrayOf(
        "/api/module-data/module/",
    )


    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf{ csrf ->
                csrf.disable()
            }
            .cors {  }
            .authorizeHttpRequests { authorizeHttpRequests ->
                authorizeHttpRequests
                    .requestMatchers("/api/auth/public/**").permitAll()
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
//                    .requestMatchers("/api/auth/private/").hasAuthority(Role.ADMIN.name)
//                    .requestMatchers(*PERMIT_ALL_COMMENT).hasAnyAuthority(Role.USER.name, Role.ADMIN.name)
//                    .requestMatchers("/api/comment/**").hasAuthority(Role.ADMIN.name)
//                    .requestMatchers(*PERMIT_ALL_COURSE).hasAnyAuthority(Role.USER.name, Role.ADMIN.name)
//                    .requestMatchers("/api/course/**").hasAnyAuthority(Role.ADMIN.name, Role.USER.name)
//                    .requestMatchers("/api/user/reset/password").permitAll()
//                    .requestMatchers(*PERMIT_ALL_USER).hasAnyAuthority(Role.USER.name, Role.ADMIN.name)
//                    .requestMatchers("/api/user/**").hasAnyAuthority(Role.ADMIN.name)
//                    .requestMatchers(*PERMIT_ALL_MODULE).hasAnyAuthority(Role.USER.name, Role.ADMIN.name)
//                    .requestMatchers("/api/module/**").hasAnyAuthority(Role.ADMIN.name, Role.USER.name)
//                    .requestMatchers(*PERMIT_ALL_MODULE_DATA).hasAnyAuthority(Role.USER.name, Role.ADMIN.name)
//                    .requestMatchers("/api/module-data/**").hasAnyAuthority(Role.ADMIN.name)
                    .anyRequest()
                    .authenticated()
            }
            .sessionManagement { SessionCreationPolicy.STATELESS }
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter::class.java)
        return http.build()
    }
}