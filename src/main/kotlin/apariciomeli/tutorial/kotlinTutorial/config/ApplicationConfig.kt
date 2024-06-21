package apariciomeli.tutorial.kotlinTutorial.config

import apariciomeli.tutorial.kotlinTutorial.repo.EndUserRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class ApplicationConfig(
    private val endUserRepository: EndUserRepository,
) {
//    private val passwordEncoder = BCryptPasswordEncoder()

    @Bean
    fun userDetailsService(): UserDetailsService {
        return UserDetailsService { username ->  endUserRepository.findEndUserByEmailIgnoreCase(username)
            .orElseThrow {UsernameNotFoundException("User not found")}}

    }

    @Bean
    fun authenticationProvider(): AuthenticationProvider {
        val authProvider = DaoAuthenticationProvider()
        authProvider.setUserDetailsService(userDetailsService())
        authProvider.setPasswordEncoder(passwordEncoder())
        return authProvider
    }

    @Bean
    fun authenticationManager(config: AuthenticationConfiguration): AuthenticationManager{
        return config.authenticationManager
    }

    @Bean
   fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }
}