package apariciomeli.tutorial.kotlinTutorial.DTO

import apariciomeli.tutorial.kotlinTutorial.model.Role
import java.util.Date

class EndUserDTO (
    var id: Int,
    var name: String,
    var email: String,
    var role: Role,
    var password: String
)

