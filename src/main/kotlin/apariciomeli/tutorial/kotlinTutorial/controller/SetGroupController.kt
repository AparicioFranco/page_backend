package apariciomeli.tutorial.kotlinTutorial.controller

import apariciomeli.tutorial.kotlinTutorial.dto.setgroup.GroupDTO
import apariciomeli.tutorial.kotlinTutorial.model.SetGroup
import apariciomeli.tutorial.kotlinTutorial.service.moduledata.SetGroupService
import org.springframework.web.bind.annotation.*

@CrossOrigin("*")
@RestController
@RequestMapping("/api/group")
class SetGroupController(
    private val setGroupService: SetGroupService,
) {
    @PostMapping("/private/add")
    fun createGroup(@RequestBody groupDTO: GroupDTO): GroupDTO {
        return setGroupService.createGroup(groupDTO)
    }

    @GetMapping("/private/course/{courseId}")
    fun getGroupsByCourseId(@PathVariable courseId: Int): List<SetGroup> {
        return setGroupService.getGroupsByCourseId(courseId).sortedBy { it.id }
    }
//
//
//    @GetMapping("/private/user")
//    fun getUsersReadModule(): List<ModuleUsersReadDTO> {
//        return moduleService.getUsersReadModule()
//    }
//
//    @PutMapping("/private/changeLock/{moduleId}")
//    fun changeLockModule(@PathVariable moduleId: Int): Module {
//        return moduleService.changeLockStatus(moduleId)
//    }


}