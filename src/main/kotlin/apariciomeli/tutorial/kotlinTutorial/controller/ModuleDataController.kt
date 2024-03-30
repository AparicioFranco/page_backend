package apariciomeli.tutorial.kotlinTutorial.controller

import apariciomeli.tutorial.kotlinTutorial.DTO.ModuleDTO
import apariciomeli.tutorial.kotlinTutorial.DTO.ModuleDataDTO
import apariciomeli.tutorial.kotlinTutorial.model.Module
import apariciomeli.tutorial.kotlinTutorial.model.ModuleData
import apariciomeli.tutorial.kotlinTutorial.service.module.ModuleService
import apariciomeli.tutorial.kotlinTutorial.service.moduledata.ModuleDataService
import org.springframework.web.bind.annotation.*

@CrossOrigin("*")
@RestController
@RequestMapping("/api/module/data")
class ModuleDataController(
    private val moduleDataService: ModuleDataService
) {
    @PostMapping
    fun createModuleData(@RequestBody moduleDataDTO: ModuleDataDTO): ModuleData {
        return moduleDataService.createModuleData(moduleDataDTO)
    }

    @GetMapping("/module/{moduleId}")
    fun getModulesDataByModuleId(@PathVariable moduleId: Int): List<ModuleData> {
        return moduleDataService.getModulesDataByModuleId(moduleId).sortedBy { it.id }
    }

    @GetMapping("/{moduleId}")
    fun getModulesContentByModuleId(@PathVariable moduleId: Int): ModuleData {
        return moduleDataService.getModulesDataContentByModuleDataId(moduleId)
    }

    @PutMapping
    fun changeModuleData(@RequestBody moduleDataDTO: ModuleDataDTO): ModuleData {
        return moduleDataService.changeModuleData(moduleDataDTO)
    }



}