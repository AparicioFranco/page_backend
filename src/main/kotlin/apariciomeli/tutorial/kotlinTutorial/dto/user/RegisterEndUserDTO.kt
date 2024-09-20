package apariciomeli.tutorial.kotlinTutorial.dto.user

import apariciomeli.tutorial.kotlinTutorial.model.Role

class RegisterEndUserDTO(
    val email: String,
    val name: String,
    val password: String,
    val role: Role
) {
}