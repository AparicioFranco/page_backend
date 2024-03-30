package apariciomeli.tutorial.kotlinTutorial.service.moduledata


import apariciomeli.tutorial.kotlinTutorial.DTO.ModuleDataDTO
import apariciomeli.tutorial.kotlinTutorial.mapper.ModuleDataMapper
import apariciomeli.tutorial.kotlinTutorial.model.ModuleData
import apariciomeli.tutorial.kotlinTutorial.repo.CourseRepository
import apariciomeli.tutorial.kotlinTutorial.repo.ModuleDataRepository
import org.springframework.stereotype.Service

@Service
class ModuleDataServiceImpl(
    private val moduleDataRepository: ModuleDataRepository,
    private val moduleDataMapper: ModuleDataMapper,
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

    override fun changeModuleData(moduleDataDTO: ModuleDataDTO): ModuleData {
        val moduleData = moduleDataMapper.toEntity(moduleDataDTO)
        return moduleDataRepository.save(moduleData) 
    }


}