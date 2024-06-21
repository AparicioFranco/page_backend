package apariciomeli.tutorial.kotlinTutorial.controller

import apariciomeli.tutorial.kotlinTutorial.DTO.CourseDTO
import apariciomeli.tutorial.kotlinTutorial.DTO.EndUserAdminViewDTO
import apariciomeli.tutorial.kotlinTutorial.model.Course
import apariciomeli.tutorial.kotlinTutorial.model.EndUser
import apariciomeli.tutorial.kotlinTutorial.service.course.CourseService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@CrossOrigin("*")
@RestController
@RequestMapping("/api/demo")
class DemoController(
    private val courseService: CourseService
) {

    @GetMapping()
    fun sayHello(): ResponseEntity<String> {
        return ResponseEntity.ok("Hello Secured")
    }
}