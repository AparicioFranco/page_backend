package apariciomeli.tutorial.kotlinTutorial.service.moduledata

import apariciomeli.tutorial.kotlinTutorial.dto.module.ModuleDataDTO
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
) : ModuleDataService {
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
    val courseOptional = courseRepository.findById(courseId)
    val modulesDataReturn = mutableListOf<List<ModuleData>>()
    if (courseOptional.isPresent) {
      return modulesDataReturn
      //            val modules = moduleRepository.findAllByCourse(courseOptional.get()).sortedBy {
      // it.id }
      //            for (module in modules) {
      //                val moduleDataTemp = moduleDataRepository.findAllByModuleId(module.id)
      //                modulesDataReturn.add(moduleDataTemp)
      //            }
    }
    return modulesDataReturn
  }

  override fun changeModuleData(moduleDataDTO: ModuleDataDTO): ModuleData {
    val moduleData = moduleDataMapper.toEntity(moduleDataDTO)
    return moduleDataRepository.save(moduleData)
  }
}
