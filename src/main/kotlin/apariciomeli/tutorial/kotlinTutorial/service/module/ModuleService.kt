package apariciomeli.tutorial.kotlinTutorial.service.module

import apariciomeli.tutorial.kotlinTutorial.DTO.ModuleDTO
import apariciomeli.tutorial.kotlinTutorial.model.Module


interface ModuleService {
    fun createModule(moduleDTO: ModuleDTO): Module
    fun getModulesByCourseId(courseId: Int): List<Module>
}