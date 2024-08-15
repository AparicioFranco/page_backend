package apariciomeli.tutorial.kotlinTutorial.service.enduser

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service

@Service
class EmailSenderService(
    private val mailSender: JavaMailSender
) {
    fun sendEMail(password: String, userEmail:String){
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
}