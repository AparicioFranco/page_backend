package apariciomeli.tutorial.kotlinTutorial.controller.auth


data class AuthenticationResponse(
    val token: String,
    val role: String
) {
}
