package apariciomeli.tutorial.kotlinTutorial.service.module

import apariciomeli.tutorial.kotlinTutorial.DTO.ModuleDTO
import apariciomeli.tutorial.kotlinTutorial.model.Module
import apariciomeli.tutorial.kotlinTutorial.model.ModuleData


interface ModuleService {
    fun createModule(moduleDTO: ModuleDTO): Module
    fun getModulesByCourseId(courseId: Int): List<Module>
    fun getModulesContentByModuleId(moduleId: Int): Module

    fun findModuleById(moduleId: Int): Module

//    fun getModulesDataByModuleId(moduleId:Int): List<ModuleData>
}