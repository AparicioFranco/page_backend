package apariciomeli.tutorial.kotlinTutorial.service.course

import apariciomeli.tutorial.kotlinTutorial.DTO.CourseDTO
import apariciomeli.tutorial.kotlinTutorial.DTO.EndUserAdminViewDTO
import apariciomeli.tutorial.kotlinTutorial.model.Course
import apariciomeli.tutorial.kotlinTutorial.model.EndUser


interface CourseService {
    fun createCourse(courseDTO: CourseDTO): Course
    fun findCourseById(courseId: Int): Course
    fun findAllCourses(): List<Course>
    fun getUsersByCourseId(courseId: Int): List<EndUserAdminViewDTO>
}