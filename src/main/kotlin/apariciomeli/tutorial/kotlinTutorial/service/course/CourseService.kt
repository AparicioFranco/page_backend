package apariciomeli.tutorial.kotlinTutorial.service.course

import apariciomeli.tutorial.kotlinTutorial.dto.course.CourseDTO
import apariciomeli.tutorial.kotlinTutorial.dto.course.CourseSendDTO
import apariciomeli.tutorial.kotlinTutorial.dto.comment.EndUserAdminViewDTO
import apariciomeli.tutorial.kotlinTutorial.dto.course.ReturnCourseDTO
import apariciomeli.tutorial.kotlinTutorial.model.Course

interface CourseService {
  fun createCourse(courseDTO: CourseDTO): ReturnCourseDTO

  fun findCourseById(courseId: Int): CourseSendDTO

  fun findAllCourses(): List<Course>

  fun getUsersByCourseId(courseId: Int): List<EndUserAdminViewDTO>
}
