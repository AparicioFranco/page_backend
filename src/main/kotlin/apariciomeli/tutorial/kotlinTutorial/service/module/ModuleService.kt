package apariciomeli.tutorial.kotlinTutorial.service.module

import apariciomeli.tutorial.kotlinTutorial.DTO.ModuleDTO
import apariciomeli.tutorial.kotlinTutorial.DTO.ModuleUsersReadDTO
import apariciomeli.tutorial.kotlinTutorial.model.Course
import apariciomeli.tutorial.kotlinTutorial.model.EndUser
import apariciomeli.tutorial.kotlinTutorial.model.Module


interface ModuleService {
    fun createModule(moduleDTO: ModuleDTO): Module
    fun getModulesByCourseId(courseId: Int): List<Module>

    fun getModulesByAvailabilityAndCourseId(courseId: Int): List<Module>
    fun getModulesContentByModuleId(moduleId: Int): Module

    fun findModuleById(moduleId: Int): Module

    fun getUsersReadModule(): List<ModuleUsersReadDTO>

//    fun getModulesDataByModuleId(moduleId:Int): List<ModuleData>
}