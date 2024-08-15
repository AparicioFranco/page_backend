package apariciomeli.tutorial.kotlinTutorial.controller

import apariciomeli.tutorial.kotlinTutorial.DTO.ModuleDataDTO
import apariciomeli.tutorial.kotlinTutorial.model.ModuleData
import apariciomeli.tutorial.kotlinTutorial.service.moduledata.ModuleDataService
import org.springframework.web.bind.annotation.*

@CrossOrigin("*")
@RestController
@RequestMapping("/api/module-data")
class ModuleDataController(
    private val moduleDataService: ModuleDataService
) {
    @PostMapping("/private/add")
    fun createModuleData(@RequestBody moduleDataDTO: ModuleDataDTO): ModuleData {
        return moduleDataService.createModuleData(moduleDataDTO)
    }

    @GetMapping("/public/module/{moduleId}")
    fun getModulesDataByModuleId(@PathVariable moduleId: Int): List<ModuleData> {
        return moduleDataService.getModulesDataByModuleId(moduleId).sortedBy { it.id }
    }

    @GetMapping("/private/course/{courseId}")
    fun getModulesDataByCourseId(@PathVariable courseId: Int): List<List<ModuleData>> {
        return moduleDataService.getModulesDataByCourseId(courseId)
    }

    @PutMapping("/private/edit")
    fun changeModuleData(@RequestBody moduleDataDTO: ModuleDataDTO): ModuleData {
        return moduleDataService.changeModuleData(moduleDataDTO)
    }



}