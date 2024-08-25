package apariciomeli.tutorial.kotlinTutorial.controller

import apariciomeli.tutorial.kotlinTutorial.dto.user.*
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


    @PostMapping("/public/new")
    fun createUser(@RequestBody endUserDTO: EndUserDTO): EndUser {
        return endUserService.createUser(endUserDTO)
    }

    @GetMapping("/private/check")
    fun getPrivate(): String{
        return "Hi"
    }

    @GetMapping("/private/get")
    fun getUsers(): List<EndUserAdminViewDTO> {
        return endUserService.findAllUsers()
    }

    @GetMapping("/private/get/{userId}")
    fun getUsersById(@PathVariable userId: Int): GetUserByIdDTO {
        return endUserService.findUserById(userId)
    }

    @PostMapping("/private/course/{userId}/{courseId}")
    fun addUserToCourse(@PathVariable userId: Int, @PathVariable courseId: Int ): EndUser{
        return endUserService.addUserToCourse(userId, courseId)
    }

    @GetMapping("/public/own/course")
    fun getCoursesByUser(@RequestHeader("Authorization") token: String): List<Course>{
        return endUserService.getCoursesByUserId(token)
    }

    @PostMapping("/public/logIn")
    fun checkUserInformation(@RequestBody userDTO: EndUserLogInDTO): EndUserCheckedDTO {
        return endUserService.checkUser(userDTO)
    }

    @PutMapping("/public/password")
    fun changePassword(@RequestHeader("Authorization") bearerToken: String, @RequestBody passwordDTO: ChangePasswordDTO): EndUser{
        return endUserService.changePassword(bearerToken, passwordDTO)
    }

    @PutMapping("/public/module/read/{moduleId}")
    fun addModuleToModuleReadList(@RequestHeader("Authorization") bearerToken: String, @PathVariable moduleId: Int): List<Module>{
        return endUserService.addModuleToModuleReadList(bearerToken, moduleId)
    }

    @GetMapping("/public/module/completed")
    fun getModulesCompletedByUserToken(@RequestHeader("Authorization") token: String): List<Module>{
        return endUserService.getModulesCompletedByUserToken(token).sortedBy { it.id }
    }

    @GetMapping("/public/module/completed/{courseId}/calendar")
    fun getMarkedDaysCalendar(@RequestHeader("Authorization") token: String, @PathVariable courseId: Int): List<Int>{
        return endUserService.getCompletedModulesForCalendar(token, courseId)
    }
}