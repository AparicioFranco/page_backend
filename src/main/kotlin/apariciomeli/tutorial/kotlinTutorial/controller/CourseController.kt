package apariciomeli.tutorial.kotlinTutorial.controller

import apariciomeli.tutorial.kotlinTutorial.DTO.CourseDTO
import apariciomeli.tutorial.kotlinTutorial.DTO.CourseSendDTO
import apariciomeli.tutorial.kotlinTutorial.DTO.EndUserAdminViewDTO
import apariciomeli.tutorial.kotlinTutorial.config.JwtService
import apariciomeli.tutorial.kotlinTutorial.model.Course
import apariciomeli.tutorial.kotlinTutorial.service.course.CourseService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@CrossOrigin("*")
@RestController
@RequestMapping("/api/course")
class CourseController(
    private val courseService: CourseService,
    private val jwtService: JwtService
) {

    @PostMapping("/private/add")
    fun createCourse(@RequestBody courseDTO: CourseDTO): Course {
        return courseService.createCourse(courseDTO)
    }

    @GetMapping("/private/get")
    fun getCourses(): List<Course> {
        val course = courseService.findAllCourses().sortedBy { it.id }
        return course
    }

    @GetMapping("/public/id/{courseId}")
    fun getCourseById(@PathVariable courseId: Int): CourseSendDTO {
        return courseService.findCourseById(courseId)
    }


    @GetMapping("/private/user/{courseId}")
    fun getUsersByCourseId(@PathVariable courseId: Int): List<EndUserAdminViewDTO> {
        return courseService.getUsersByCourseId(courseId)
    }
}