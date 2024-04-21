package apariciomeli.tutorial.kotlinTutorial.controller

import apariciomeli.tutorial.kotlinTutorial.DTO.CourseDTO
import apariciomeli.tutorial.kotlinTutorial.DTO.EndUserAdminViewDTO
import apariciomeli.tutorial.kotlinTutorial.model.Course
import apariciomeli.tutorial.kotlinTutorial.model.EndUser
import apariciomeli.tutorial.kotlinTutorial.service.course.CourseService
import org.springframework.web.bind.annotation.*

@CrossOrigin("*")
@RestController
@RequestMapping("/api/course")
class CourseController(
    private val courseService: CourseService
) {

    @PostMapping()
    fun createCourse(@RequestBody courseDTO: CourseDTO): Course {
        return courseService.createCourse(courseDTO)
    }

    @GetMapping()
    fun getCourses(): List<Course> {
        return courseService.findAllCourses().sortedBy { it.id }
    }

    @GetMapping("/{courseId}")
    fun getCourseById(@PathVariable courseId: Int): Course {
        return courseService.findCourseById(courseId)
    }

    @GetMapping("/user/{courseId}")
    fun getUsersByCourseId(@PathVariable courseId: Int): List<EndUserAdminViewDTO> {
        return courseService.getUsersByCourseId(courseId)
    }
}