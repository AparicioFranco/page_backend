package apariciomeli.tutorial.kotlinTutorial.service.course

import apariciomeli.tutorial.kotlinTutorial.dto.course.CourseDTO
import apariciomeli.tutorial.kotlinTutorial.dto.course.CourseSendDTO
import apariciomeli.tutorial.kotlinTutorial.dto.user.EndUserAdminViewDTO
import apariciomeli.tutorial.kotlinTutorial.model.Course

interface CourseService {
  fun createCourse(courseDTO: CourseDTO): Course

  fun findCourseById(courseId: Int): CourseSendDTO

  fun findAllCourses(): List<Course>

  fun getUsersByCourseId(courseId: Int): List<EndUserAdminViewDTO>
}
