package apariciomeli.tutorial.kotlinTutorial.controller

import apariciomeli.tutorial.kotlinTutorial.DTO.EndUserDTO
import apariciomeli.tutorial.kotlinTutorial.model.Course
import apariciomeli.tutorial.kotlinTutorial.model.EndUser
import apariciomeli.tutorial.kotlinTutorial.service.course.EndUserService
import org.springframework.web.bind.annotation.*

@CrossOrigin("*")
@RestController
@RequestMapping("/api/user")
class EndUserController(
    private val endUserService: EndUserService
) {

    @PostMapping()
    fun createCourse(@RequestBody endUserDTO: EndUserDTO): EndUser {
        return endUserService.createUser(endUserDTO)
    }

    @GetMapping()
    fun getUsers(): List<EndUser> {
        return endUserService.findAllUsers()
    }

    @GetMapping("/{userId}")
    fun getUsersById(@PathVariable userId: Int): EndUser {
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

}