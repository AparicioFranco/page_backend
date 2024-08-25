package apariciomeli.tutorial.kotlinTutorial.service.module

import apariciomeli.tutorial.kotlinTutorial.dto.module.ModuleDTO
import apariciomeli.tutorial.kotlinTutorial.dto.module.ModuleUsersReadDTO
import apariciomeli.tutorial.kotlinTutorial.dto.setgroup.GroupModulesDTO
import apariciomeli.tutorial.kotlinTutorial.model.Module

interface ModuleService {
  fun createModule(moduleDTO: ModuleDTO): Module

  fun getModulesByCourseId(courseId: Int): List<Module>

  fun getAvailableModulesByCourseId(courseId: Int): List<Module>

  fun getModulesByGroupId(groupId: Int): List<Module>

  fun getAvailableModulesByGroupId(groupId: Int): List<Module>

  fun getModulesByAvailabilityAndCourseId(courseId: Int): List<GroupModulesDTO>

  fun getModulesContentByModuleId(moduleId: Int): Module

  fun findModuleById(moduleId: Int): Module

  fun getUsersReadModule(): List<ModuleUsersReadDTO>

  fun changeLockStatus(moduleId: Int): Module
}
