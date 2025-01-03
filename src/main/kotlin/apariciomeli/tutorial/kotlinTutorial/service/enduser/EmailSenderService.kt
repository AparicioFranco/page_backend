package apariciomeli.tutorial.kotlinTutorial.service.enduser

import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service

@Service
class EmailSenderService(
    private val mailSender: JavaMailSender
) {
    fun sendRecoverEmail(password: String, userEmail:String){
        val template = SimpleMailMessage()
        template.subject = "Nueva contraseña"
        template.setText("""
            
            Tu nueva contraseña para la operación es: $password
            
            
            Puedes entrar en: https://listadelimperio.com/
            
        """.trimIndent()
        )
        template.setTo(userEmail)

        mailSender.send(template)

        println("Mail sent")
    }

    fun sendCreationEmail(password: String, userEmail:String){
        val template = SimpleMailMessage()
        template.subject = "Credenciales para la operación"
        template.setText("""
            
            Bienvenido a la Lista del Imperio!
            
            Tu email para la operación es: $userEmail
            
            Tu contraseña para la operación es: $password
            
            
            Puedes entrar en: https://listadelimperio.com/
            
        """.trimIndent()
        )
        template.setTo(userEmail)

        mailSender.send(template)

        println("Mail sent")
    }
}