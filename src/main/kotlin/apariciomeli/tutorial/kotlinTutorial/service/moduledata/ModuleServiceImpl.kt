package apariciomeli.tutorial.kotlinTutorial.service.moduledata


import apariciomeli.tutorial.kotlinTutorial.DTO.ModuleDataDTO
import apariciomeli.tutorial.kotlinTutorial.mapper.ModuleDataMapper
import apariciomeli.tutorial.kotlinTutorial.model.ModuleData
import apariciomeli.tutorial.kotlinTutorial.repo.CourseRepository
import apariciomeli.tutorial.kotlinTutorial.repo.ModuleDataRepository
import apariciomeli.tutorial.kotlinTutorial.repo.ModuleRepository
import org.springframework.stereotype.Service

@Service
class ModuleDataServiceImpl(
    private val moduleDataRepository: ModuleDataRepository,
    private val moduleDataMapper: ModuleDataMapper,
    private val moduleRepository: ModuleRepository,
    private val courseRepository: CourseRepository
): ModuleDataService {
    override fun createModuleData(moduleDataDTO: ModuleDataDTO): ModuleData {
        val moduleData = moduleDataMapper.toEntity(moduleDataDTO)
        return moduleDataRepository.save(moduleData)
    }

    override fun getModulesDataByModuleId(moduleId: Int): List<ModuleData> {
        return moduleDataRepository.findAllByModuleId(moduleId)
    }

    override fun getModulesDataContentByModuleDataId(moduleDataId: Int): ModuleData {
        TODO("Not yet implemented")
    }

    override fun getModulesDataByCourseId(courseId: Int): List<List<ModuleData>> {
        val course = courseRepository.findById(courseId).get()
        val modules = moduleRepository.findAllByCourse(course).sortedBy { it.id }
        val modulesDataReturn = mutableListOf<List<ModuleData>>()
        for (module in modules) {
            var moduleDataTemp = moduleDataRepository.findAllByModuleId(module.id)
            modulesDataReturn.add(moduleDataTemp)
        }
        return modulesDataReturn
    }

    override fun changeModuleData(moduleDataDTO: ModuleDataDTO): ModuleData {
        val moduleData = moduleDataMapper.toEntity(moduleDataDTO)
        return moduleDataRepository.save(moduleData) 
    }


}