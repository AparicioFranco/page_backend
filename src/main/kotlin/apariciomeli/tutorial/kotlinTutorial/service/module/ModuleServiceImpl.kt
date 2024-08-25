package apariciomeli.tutorial.kotlinTutorial.service.module

import apariciomeli.tutorial.kotlinTutorial.dto.module.ModuleDTO
import apariciomeli.tutorial.kotlinTutorial.dto.module.ModuleUsersReadDTO
import apariciomeli.tutorial.kotlinTutorial.dto.setgroup.GroupModulesDTO
import apariciomeli.tutorial.kotlinTutorial.dto.setgroup.MinimalModuleDTO
import apariciomeli.tutorial.kotlinTutorial.dto.user.EndUserAdminViewDTO
import apariciomeli.tutorial.kotlinTutorial.mapper.EndUserAdminViewMapper
import apariciomeli.tutorial.kotlinTutorial.mapper.ModuleMapper
import apariciomeli.tutorial.kotlinTutorial.model.Module
import apariciomeli.tutorial.kotlinTutorial.repo.CourseRepository
import apariciomeli.tutorial.kotlinTutorial.repo.ModuleRepository
import apariciomeli.tutorial.kotlinTutorial.repo.SetGroupRepository
import org.springframework.stereotype.Service

@Service
class ModuleServiceImpl(
    private val moduleRepository: ModuleRepository,
    private val moduleMapper: ModuleMapper,
    private val courseRepository: CourseRepository,
    private val endUserAdminViewMapper: EndUserAdminViewMapper,
    private val setGroupRepository: SetGroupRepository
) : ModuleService {

  override fun createModule(moduleDTO: ModuleDTO): Module {
    val module = moduleMapper.toEntity(moduleDTO)
    return moduleRepository.save(module)
  }

  override fun getModulesByCourseId(courseId: Int): List<Module> {
    val courseOptional = courseRepository.findById(courseId)
    if (courseOptional.isPresent) {
      val coursePresent = courseOptional.get()
      val allModulesByCourseIdList = mutableListOf<Module>()
      setGroupRepository.findAllByCourse(coursePresent).forEach {
        allModulesByCourseIdList.addAll(getModulesByGroupId(it.id))
      }
      return allModulesByCourseIdList
    }
    throw (Exception("Not found"))
  }

  override fun getAvailableModulesByCourseId(courseId: Int): List<Module> {
    val courseOptional = courseRepository.findById(courseId)
    if (courseOptional.isPresent) {
      val coursePresent = courseOptional.get()
      val availableModulesByCourseIdList = mutableListOf<Module>()
      setGroupRepository.findAllByCourse(coursePresent).forEach {
        availableModulesByCourseIdList.addAll(getAvailableModulesByGroupId(it.id))
      }
      return availableModulesByCourseIdList
    }
    throw (Exception("Not found"))
  }

  override fun getModulesByGroupId(groupId: Int): List<Module> {
    val groupOptional = setGroupRepository.findById(groupId)
    if (groupOptional.isPresent) {
      val groupPresent = groupOptional.get()
      return moduleRepository.findAllByGroup(groupPresent)
    }
    throw Exception("Group not found")
  }

  override fun getAvailableModulesByGroupId(groupId: Int): List<Module> {
    val groupOptional = setGroupRepository.findById(groupId)
    if (groupOptional.isPresent) {
      val groupPresent = groupOptional.get()
      return moduleRepository.findAllByGroupAndLocked(groupPresent, false)
    }
    throw Exception("Group not found")
  }

  override fun getModulesByAvailabilityAndCourseId(courseId: Int): List<GroupModulesDTO> {
    val courseOptional = courseRepository.findById(courseId)
    if (courseOptional.isPresent) {
      val coursePresent = courseOptional.get()
      val listWithModulesAndGroups = mutableListOf<GroupModulesDTO>()
      setGroupRepository.findAllByCourse(coursePresent).forEach { setGroupIt ->
        listWithModulesAndGroups.add(
            GroupModulesDTO(
                groupId = setGroupIt.id,
                groupName = setGroupIt.name,
                modules =
                    moduleRepository
                        .findAllByGroupAndLocked(setGroupIt, false)
                        .map { moduleIt ->
                          MinimalModuleDTO(moduleId = moduleIt.id, moduleName = moduleIt.name)
                        }
                        .sortedBy { moduleSortIt -> moduleSortIt.moduleId }))
      }
      return listWithModulesAndGroups
    }
    throw (Exception("Not found"))
  }

  override fun getModulesContentByModuleId(moduleId: Int): Module {
    val moduleOptional = moduleRepository.findById(moduleId)
    if (moduleOptional.isPresent) {
      return moduleOptional.get()
    }
    throw Exception("Not found")
  }

  override fun findModuleById(moduleId: Int): Module {
    return moduleRepository.findById(moduleId).orElseThrow()
  }

  override fun getUsersReadModule(): List<ModuleUsersReadDTO> {
    val usersReadModulesList = mutableListOf<ModuleUsersReadDTO>()
    val moduleLists = moduleRepository.findAll().sortedBy { it.id }
    for (module in moduleLists) {
      val usersAdminView = mutableListOf<EndUserAdminViewDTO>()
      module.users
          .sortedBy { it.id }
          .forEach { usersAdminView.add(endUserAdminViewMapper.fromEntity(it)) }
      usersReadModulesList.add(
          ModuleUsersReadDTO(id = module.id, name = module.name, users = usersAdminView))
    }
    return usersReadModulesList
  }

  override fun changeLockStatus(moduleId: Int): Module {
    val moduleToChange = moduleRepository.findById(moduleId).get()
    val newModule = moduleToChange.copy(locked = !moduleToChange.locked)
    moduleRepository.save(newModule)
    return newModule
  }
}
