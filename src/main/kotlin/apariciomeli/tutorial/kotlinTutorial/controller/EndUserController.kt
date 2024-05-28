package apariciomeli.tutorial.kotlinTutorial.controller

import apariciomeli.tutorial.kotlinTutorial.DTO.*
import apariciomeli.tutorial.kotlinTutorial.model.Comment
import apariciomeli.tutorial.kotlinTutorial.model.Course
import apariciomeli.tutorial.kotlinTutorial.model.EndUser
import apariciomeli.tutorial.kotlinTutorial.model.Module
import apariciomeli.tutorial.kotlinTutorial.service.course.EndUserService
import org.springframework.web.bind.annotation.*

@CrossOrigin("*")
@RestController
@RequestMapping("/api/user")
class EndUserController(
    private val endUserService: EndUserService
) {


    @PostMapping()
    fun createUser(@RequestBody endUserDTO: EndUserDTO): EndUser {
        return endUserService.createUser(endUserDTO)
    }

    @GetMapping()
    fun getUsers(): List<EndUserAdminViewDTO> {
        return endUserService.findAllUsers()
    }

    @GetMapping("/{userId}")
    fun getUsersById(@PathVariable userId: Int): GetUserByIdDTO {
        return endUserService.findUserById(userId)
    }

    @PostMapping("/course/{userId}/{courseId}")
    fun addUserToCourse(@PathVariable userId: Int, @PathVariable courseId: Int ): EndUser{
        return endUserService.addUserToCourse(userId, courseId)
    }

    @GetMapping("/course/{userId}")
    fun getCoursesByUser(@PathVariable userId: Int): List<Course>{
        return endUserService.getCoursesByUserId(userId)
    }

    @PostMapping("/logIn")
    fun checkUserInformation(@RequestBody userDTO: EndUserLogInDTO ): EndUserCheckedDTO {
        return endUserService.checkUser(userDTO)
    }

    @PutMapping("/password")
    fun changePassword(@RequestBody passwordDTO: ChangePasswordDTO): EndUser{
        return endUserService.changePassword(passwordDTO)
    }

    @PutMapping("/module/{userId}/{moduleId}")
    fun addModuleToModuleReadList(@PathVariable userId: Int, @PathVariable moduleId: Int): List<Module>{
        return endUserService.addModuleToModuleReadList(userId, moduleId)
    }

    @GetMapping("/module/{userId}")
    fun getModulesCompletedByUserId(@PathVariable userId: Int): List<Module>{
        return endUserService.getModulesCompletedByUserId(userId).sortedBy { it.id }
    }

    @GetMapping("/reset/password")
    fun resetPassword(@PathVariable userId: Int): List<Module>{
        return endUserService.getModulesCompletedByUserId(userId).sortedBy { it.id }
    }

}