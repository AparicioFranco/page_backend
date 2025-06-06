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
        template.text = """
                
                Tu nueva contraseña es: $password
                
                
                Aquí entras otra vez: https://listadelimperio.com/
                
            """.trimIndent()
        template.setTo(userEmail)

        mailSender.send(template)

    }

    fun sendCreationEmail(password: String, userEmail:String){
        val template = SimpleMailMessage()
        template.subject = "INSTRUCCIONES DE ACCESO"
        template.text = """
                
                Amigo imperial, muy bienvenido. 
                
                
                Presta atención que aquí te doy las instrucciones de acceso. 
                
                
                La puerta de acceso la abres entrando aquí: https://listadelimperio.com/
                
                
                Tu email para acceder es: $userEmail
                
                Tu contraseña para acceder es: $password
                
                
                
                Estoy feliz de acompañarte más cerca.
                
                Abrazo largo. 
                           
                
            """.trimIndent()
        template.setTo(userEmail)

        mailSender.send(template)

    }

    fun sendPurchasedAudiobook(userEmail:String){
        val template = SimpleMailMessage()
        template.subject = "Acceso Audiolibro"
        template.text = """
                
                Bienvenido otra vez por aquí, amigo imperial. 


                Para acceder al audiolibro solo debes entrar aquí: 
                
                https://www.listadelimperio.com/
                
                Ingresas con el mismo email y contraseña que usaste para otras Operaciones. 
                
                
                Abrazo lago. 
                
                
                PD. Feliz de volver a encontrarnos. 
                           
                
            """.trimIndent()
        template.setTo(userEmail)

        mailSender.send(template)

    }
}