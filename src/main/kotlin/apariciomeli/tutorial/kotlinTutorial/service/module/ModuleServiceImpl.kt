package apariciomeli.tutorial.kotlinTutorial.service.module

import apariciomeli.tutorial.kotlinTutorial.DTO.EndUserAdminViewDTO
import apariciomeli.tutorial.kotlinTutorial.model.Module
import apariciomeli.tutorial.kotlinTutorial.DTO.ModuleDTO
import apariciomeli.tutorial.kotlinTutorial.DTO.ModuleUsersReadDTO
import apariciomeli.tutorial.kotlinTutorial.mapper.EndUserAdminViewMapper
import apariciomeli.tutorial.kotlinTutorial.mapper.ModuleMapper
import apariciomeli.tutorial.kotlinTutorial.repo.CourseRepository
import apariciomeli.tutorial.kotlinTutorial.repo.ModuleRepository
import org.springframework.stereotype.Service

@Service
class ModuleServiceImpl(
    private val moduleRepository: ModuleRepository,
    private val moduleMapper: ModuleMapper,
    private val courseRepository: CourseRepository,
    private val endUserAdminViewMapper: EndUserAdminViewMapper,
//    private val moduleDataServiceImpl: ModuleDataServiceImpl
): ModuleService {

    override fun createModule(moduleDTO: ModuleDTO): Module {
        val module = moduleMapper.toEntity(moduleDTO)
//        val course = courseRepository.findById(moduleDTO.courseId).get()
//        val addedModuleCourse = course.modules.add(module)
//        courseRepository.save(course)
        return moduleRepository.save(module)
    }

    override fun getModulesByCourseId(courseId: Int): List<Module> {
        val course = courseRepository.findById(courseId)
        if (course.isPresent){
            return moduleRepository.findAllByCourse(course.get())
        }else{
            throw (Exception("Not found"))
        }
    }

    override fun getModulesByAvailabilityAndCourseId(courseId: Int): List<Module> {
        val course = courseRepository.findById(courseId)
        if (course.isPresent){
            return moduleRepository.findAllByCourseAndLocked(course.get(),false)
        }else{
            throw (Exception("Not found"))
        }
    }

    override fun getModulesContentByModuleId(moduleId: Int): Module {
        return moduleRepository.findById(moduleId).get()
    }

    override fun findModuleById(moduleId: Int): Module {
        return moduleRepository.findById(moduleId).orElseThrow()
    }

    override fun getUsersReadModule(): List<ModuleUsersReadDTO> {
        val usersReadModulesList = mutableListOf<ModuleUsersReadDTO>()
        val moduleLists = moduleRepository.findAll().sortedBy { it.id }
        for (module in moduleLists){
            val usersAdminView = mutableListOf<EndUserAdminViewDTO>()
            module.users.sortedBy { it.id }.forEach { usersAdminView.add(endUserAdminViewMapper.fromEntity(it)) }
            usersReadModulesList.add(ModuleUsersReadDTO(id = module.id, name = module.name, users = usersAdminView))
        }
        return usersReadModulesList
    }

//    override fun getModulesDataByModuleId(moduleId: Int): List<ModuleData> {
//        return moduleDataServiceImpl.getModulesDataByModuleId(moduleId)
//    }
}