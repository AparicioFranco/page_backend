package apariciomeli.tutorial.kotlinTutorial

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.event.EventListener
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.JavaMailSenderImpl
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
                    .allowedOrigins("http://localhost:3000", "https://apariciomeli.com")
                    .allowedMethods("*")
            }
        }
    }

//	@Bean
//	fun getJavaMailSender(): JavaMailSender {
//		val mailSender = JavaMailSenderImpl()
//		mailSender.host = "smtp.gmail.com"
//		mailSender.port = 587
//
//		mailSender.username = "melina@apariciomeli.com"
//		mailSender.password = "Hondacrv117"
//
//		val props = mailSender.javaMailProperties
//		props["mail.transport.protocol"] = "smtp"
//		props["mail.smtp.auth"] = "true"
//		props["mail.smtp.starttls.enable"] = "true"
//		props["mail.debug"] = "true"
//		props["mail.mime.address.strict"] = "false"
//
//		return mailSender
//	}

}

fun main(args: Array<String>) {
    runApplication<KotlinTutorialApplication>(*args)
}

