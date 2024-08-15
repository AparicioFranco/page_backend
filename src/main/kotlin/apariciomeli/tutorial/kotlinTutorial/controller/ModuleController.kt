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
    @PostMapping("/private/add")
    fun createModule(@RequestBody moduleDTO: ModuleDTO): Module {
        return moduleService.createModule(moduleDTO)
    }

    @GetMapping("/private/course/{courseId}")
    fun getModulesByCourseId(@PathVariable courseId: Int): List<Module> {
        return moduleService.getModulesByCourseId(courseId).sortedBy { it.id }
    }

    @GetMapping("/public/course/available/{courseId}")
    fun getAvailableModulesByCourseId(@PathVariable courseId: Int): List<Module> {
        return moduleService.getModulesByAvailabilityAndCourseId(courseId).sortedBy { it.id }
    }

    @GetMapping("/public/{moduleId}")
    fun getModulesContentByModuleId(@PathVariable moduleId: Int): Module {
        return moduleService.getModulesContentByModuleId(moduleId)
    }

    @GetMapping("/private/user")
    fun getUsersReadModule(): List<ModuleUsersReadDTO> {
        return moduleService.getUsersReadModule()
    }

    @PutMapping("/private/changeLock/{moduleId}")
    fun changeLockModule(@PathVariable moduleId: Int): Module {
        return moduleService.changeLockStatus(moduleId)
    }


}