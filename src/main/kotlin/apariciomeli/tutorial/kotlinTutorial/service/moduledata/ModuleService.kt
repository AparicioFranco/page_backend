package apariciomeli.tutorial.kotlinTutorial.service.moduledata

import apariciomeli.tutorial.kotlinTutorial.DTO.ModuleDTO
import apariciomeli.tutorial.kotlinTutorial.DTO.ModuleDataDTO
import apariciomeli.tutorial.kotlinTutorial.model.Module
import apariciomeli.tutorial.kotlinTutorial.model.ModuleData


interface ModuleDataService {
    fun createModuleData(moduleDataDTO: ModuleDataDTO): ModuleData
    fun getModulesDataByModuleId(moduleId: Int): List<ModuleData>
    fun getModulesDataContentByModuleDataId(moduleDataId: Int): ModuleData

    fun changeModuleData(moduleDataDTO: ModuleDataDTO): ModuleData
}