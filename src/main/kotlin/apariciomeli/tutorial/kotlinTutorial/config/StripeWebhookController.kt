package apariciomeli.tutorial.kotlinTutorial.config

import apariciomeli.tutorial.kotlinTutorial.service.course.EndUserServiceImpl
import com.stripe.model.checkout.Session
import com.stripe.net.Webhook
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/webhook")
class StripeWebhookController(
    private val endUserServiceImpl: EndUserServiceImpl // Your service to create users
) {

    var paymentLinkCourseMap = mutableMapOf(
        //enigma
        "plink_1R0T9MPUWbepicWqHMUrCPGy" to 5,
        //Audiolibro
        "plink_1QZhlIAwMBiGh6CvvOFLN3nT" to 5,
    )

//    @PostMapping
//    fun handleStripeWebhook(
//        @RequestBody payload: String,
//        @RequestHeader("Stripe-Signature") sigHeader: String?
//    ): ResponseEntity<String> {
////        println("hola")
//        return try {
//            val event = Webhook.constructEvent(payload, sigHeader, endpointSecret)
//
//            when (event.type) {
//                "checkout.session.completed" -> {
//                    println(payload)
//                    val session = event.dataObjectDeserializer.getObject().orElse(null) as? Session
//                    val email = session?.customerEmail
//                    println("✅ Payment successful for: $email")
//                }
//                else -> println("⚠️ Ignored event: ${event.type}")
//            }
//
//            ResponseEntity.ok("Webhook received")
//        } catch (e: Exception) {
//            println("❌ Webhook error: ${e.message}")
//            ResponseEntity.badRequest().body("Webhook error: ${e.message}")
//        }
//    }

    @PostMapping("/checkout")
    fun handleStripeWebhook(@RequestBody payload: String, @RequestHeader("Stripe-Signature") sigHeader: String?): ResponseEntity<String> {
        val endpointSecret = System.getenv("WEBHOOK_ENDPOINT_SECRET")
        println("Endpoint entered")
        println(payload)
        return try {
            val event = Webhook.constructEvent(payload, sigHeader, endpointSecret)


            if (event.type == "checkout.session.completed") {
                println("Event entered")

                val session = event.dataObjectDeserializer.getObject().orElse(null) as Session
                val sessionPaymentLinkId = session.paymentLink // This contains the Payment Link ID
                val email = session.customerEmail ?: return ResponseEntity.badRequest().body("No email found")


                if (paymentLinkCourseMap.containsKey(sessionPaymentLinkId)) {
                    endUserServiceImpl.createUserAndAddToCourse(email, paymentLinkCourseMap.getOrDefault(sessionPaymentLinkId, -1))
                    return ResponseEntity.ok("User created for specific payment link")
                } else {
                    return ResponseEntity.ok("Ignored payment link: $sessionPaymentLinkId")
                }
            } else {
                println("Event Ignored")
                ResponseEntity.ok("Event ignored")
            }
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Webhook error: ${e.message}")
        }
    }
}