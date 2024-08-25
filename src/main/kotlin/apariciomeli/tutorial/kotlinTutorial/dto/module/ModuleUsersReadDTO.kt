package apariciomeli.tutorial.kotlinTutorial.dto.module

import apariciomeli.tutorial.kotlinTutorial.dto.user.EndUserAdminViewDTO

class ModuleUsersReadDTO (
    val id: Int,
    val name: String,
    val users: List<EndUserAdminViewDTO>
)