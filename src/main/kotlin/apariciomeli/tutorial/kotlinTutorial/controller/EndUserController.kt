package apariciomeli.tutorial.kotlinTutorial.controller

import apariciomeli.tutorial.kotlinTutorial.controller.auth.AuthenticationResponse
import apariciomeli.tutorial.kotlinTutorial.dto.comment.EndUserAdminViewDTO
import apariciomeli.tutorial.kotlinTutorial.dto.user.*
import apariciomeli.tutorial.kotlinTutorial.model.Course
import apariciomeli.tutorial.kotlinTutorial.model.EndUser
import apariciomeli.tutorial.kotlinTutorial.model.Module
import apariciomeli.tutorial.kotlinTutorial.repo.CourseRepository
import apariciomeli.tutorial.kotlinTutorial.service.course.EndUserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@CrossOrigin("*")
@RestController
@RequestMapping("/api/user")
class EndUserController(
    private val endUserService: EndUserService
) {

    @PostMapping("/private/new")
    fun createUser(@RequestBody endUserDTO: EndUserDTO): ReturnEndUserDTO {
        return endUserService.createUser(endUserDTO)
    }

    @PostMapping("/private/new-from-list")
    fun createUserFromList(@RequestBody request: EmailListRequest): Int {
        return endUserService.createUserFromList(request.emails)
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

    @GetMapping("/private/get/course/{courseId}")
    fun getUsersByCourseId(@PathVariable courseId: Int): List<EndUserAdminViewDTO> {
        return endUserService.getUsersByCourseId(courseId)
    }

    @PostMapping("/private/course/{userId}/{courseId}")
    fun addUserToCourse(@PathVariable userId: Int, @PathVariable courseId: Int ): ReturnEndUserDTO{
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
    fun changePassword(@RequestHeader("Authorization") bearerToken: String, @RequestBody passwordDTO: ChangePasswordDTO): ReturnEndUserDTO{
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

    @GetMapping("/public/check/{courseId}")
    fun checkUserInCourse(@RequestHeader("Authorization") token: String, @PathVariable courseId: Int): ResponseEntity<Int> {
        return endUserService.checkUserInCourse(token, courseId)
    }
}