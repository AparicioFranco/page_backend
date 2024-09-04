package apariciomeli.tutorial.kotlinTutorial.dto.setgroup

import apariciomeli.tutorial.kotlinTutorial.dto.module.ModuleDTO

class GroupModulesDTO(
    val groupId: Int,
    val groupName: String,
    val modules: List<MinimalModuleDTO>
)
