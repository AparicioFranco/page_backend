package apariciomeli.tutorial.kotlinTutorial.service.course

import apariciomeli.tutorial.kotlinTutorial.dto.course.CourseDTO
import apariciomeli.tutorial.kotlinTutorial.dto.course.CourseSendDTO
import apariciomeli.tutorial.kotlinTutorial.dto.course.CustomDateDTO
import apariciomeli.tutorial.kotlinTutorial.dto.user.EndUserAdminViewDTO
import apariciomeli.tutorial.kotlinTutorial.mapper.CourseMapper
import apariciomeli.tutorial.kotlinTutorial.mapper.EndUserAdminViewMapper
import apariciomeli.tutorial.kotlinTutorial.model.Course
import apariciomeli.tutorial.kotlinTutorial.repo.CourseRepository
import org.springframework.stereotype.Service

@Service
class CourseServiceImpl(
    private val courseRepository: CourseRepository,
    private val courseMapper: CourseMapper,
    private val endUserAdminViewMapper: EndUserAdminViewMapper
) : CourseService {

  override fun createCourse(courseDTO: CourseDTO): Course {
    val course = courseMapper.toEntity(courseDTO)
    return courseRepository.save(course)
  }

  override fun findCourseById(courseId: Int): CourseSendDTO {
    val courseOptional = courseRepository.findById(courseId)
    if (courseOptional.isPresent) {
      val coursePresent = courseOptional.get()
      val courseDate = coursePresent.startDate
      return CourseSendDTO(
          id = coursePresent.id,
          name = coursePresent.name,
          description = coursePresent.description,
          startDate =
              CustomDateDTO(
                  year = courseDate.year,
                  month = courseDate.month.value,
                  day = courseDate.dayOfMonth))
    }
    throw Exception("Course not found")
  }

  override fun findAllCourses(): List<Course> {
    return courseRepository.findAll()
  }

  override fun getUsersByCourseId(courseId: Int): List<EndUserAdminViewDTO> {
    val course = courseRepository.findById(courseId).get()
    val usersInCourse = mutableListOf<EndUserAdminViewDTO>()
    course.users
        .toList()
        .sortedBy { it.id }
        .forEach { usersInCourse.add(endUserAdminViewMapper.fromEntity(it)) }
    return usersInCourse
  }
}
