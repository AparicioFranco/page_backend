package apariciomeli.tutorial.kotlinTutorial.controller

import apariciomeli.tutorial.kotlinTutorial.DTO.ModuleDTO
import apariciomeli.tutorial.kotlinTutorial.model.Module
import apariciomeli.tutorial.kotlinTutorial.service.module.ModuleService
import org.springframework.web.bind.annotation.*

@CrossOrigin("*")
@RestController
@RequestMapping("/api/module")
class ModuleController(
    private val moduleService: ModuleService
) {
    @PostMapping
    fun createModule(@RequestBody moduleDTO: ModuleDTO): Module {
        return moduleService.createModule(moduleDTO)
    }

    @GetMapping("/{courseId}")
    fun getModulesByCourseId(@PathVariable courseId: Int): List<Module> {
        return moduleService.getModulesByCourseId(courseId)
    }


}