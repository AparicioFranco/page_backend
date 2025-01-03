package apariciomeli.tutorial.kotlinTutorial.service.moduledata

import apariciomeli.tutorial.kotlinTutorial.dto.module.ModuleDataDTO
import apariciomeli.tutorial.kotlinTutorial.dto.module.ModuleDataTestDTO
import apariciomeli.tutorial.kotlinTutorial.model.ModuleData
import org.springframework.web.multipart.MultipartFile

interface ModuleDataService {
  fun createModuleData(moduleData:ModuleData): ModuleData

  fun getModulesDataByModuleId(moduleId: Int): List<ModuleData>

  fun getModulesDataContentByModuleDataId(moduleDataId: Int): ModuleData

  fun getModulesDataByCourseId(courseId: Int): List<List<ModuleData>>

  fun changeModuleData(moduleDataDTO: ModuleDataDTO): ModuleData

  fun test(moduleDataTestDTO: ModuleDataTestDTO, file: MultipartFile)

  fun uploadFile(file: MultipartFile, moduleDataTestDTO: ModuleDataTestDTO): ModuleData
}
