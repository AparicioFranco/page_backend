package apariciomeli.tutorial.kotlinTutorial.controller

import apariciomeli.tutorial.kotlinTutorial.DTO.ModuleDTO
import apariciomeli.tutorial.kotlinTutorial.DTO.ModuleUsersReadDTO
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

    @GetMapping("/course/{courseId}")
    fun getModulesByCourseId(@PathVariable courseId: Int): List<Module> {
        return moduleService.getModulesByCourseId(courseId).sortedBy { it.id }
    }

    @GetMapping("/course/available/{courseId}")
    fun getAvailableModulesByCourseId(@PathVariable courseId: Int): List<Module> {
        return moduleService.getModulesByAvailabilityAndCourseId(courseId).sortedBy { it.id }
    }

    @GetMapping("/{moduleId}")
    fun getModulesContentByModuleId(@PathVariable moduleId: Int): Module {
        return moduleService.getModulesContentByModuleId(moduleId)
    }

    @GetMapping("/user")
    fun getUsersReadModule(): List<ModuleUsersReadDTO> {
        return moduleService.getUsersReadModule()
    }


}