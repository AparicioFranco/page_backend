package apariciomeli.tutorial.kotlinTutorial.service.module

import apariciomeli.tutorial.kotlinTutorial.model.Module
import apariciomeli.tutorial.kotlinTutorial.DTO.ModuleDTO
import apariciomeli.tutorial.kotlinTutorial.mapper.ModuleMapper
import apariciomeli.tutorial.kotlinTutorial.repo.CourseRepository
import apariciomeli.tutorial.kotlinTutorial.repo.ModuleRepository
import org.springframework.stereotype.Service

@Service
class ModuleServiceImpl(
    private val moduleRepository: ModuleRepository,
    private val moduleMapper: ModuleMapper,
    private val courseRepository: CourseRepository
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

    override fun getModulesContentByModuleId(moduleId: Int): Module {
        return moduleRepository.findById(moduleId).get()
    }
}