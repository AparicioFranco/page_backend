package apariciomeli.tutorial.kotlinTutorial

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@SpringBootApplication(exclude = [SecurityAutoConfiguration::class])
class KotlinTutorialApplication {

  @Bean
  fun cors(): WebMvcConfigurer {
    return object : WebMvcConfigurer {
      override fun addCorsMappings(registry: CorsRegistry) {
        registry
            .addMapping("/**")
            .allowedOrigins("http://localhost:3000", "https://listadelimperio.com/")
            .allowedMethods("*")
      }
    }
  }
}

fun main(args: Array<String>) {
  runApplication<KotlinTutorialApplication>(*args)
}
