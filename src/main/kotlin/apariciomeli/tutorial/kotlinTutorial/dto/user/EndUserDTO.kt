package apariciomeli.tutorial.kotlinTutorial.dto.user

import apariciomeli.tutorial.kotlinTutorial.model.Role

class EndUserDTO (
    var id: Int,
    var name: String,
    var email: String,
    var role: Role,
    var password: String
)

