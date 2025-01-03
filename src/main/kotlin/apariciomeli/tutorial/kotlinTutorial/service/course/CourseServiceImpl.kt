package apariciomeli.tutorial.kotlinTutorial.service.course

import apariciomeli.tutorial.kotlinTutorial.dto.course.CourseDTO
import apariciomeli.tutorial.kotlinTutorial.dto.course.CourseSendDTO
import apariciomeli.tutorial.kotlinTutorial.dto.course.CustomDateDTO
import apariciomeli.tutorial.kotlinTutorial.dto.comment.EndUserAdminViewDTO
import apariciomeli.tutorial.kotlinTutorial.dto.course.ReturnCourseDTO
import apariciomeli.tutorial.kotlinTutorial.mapper.CourseMapper
import apariciomeli.tutorial.kotlinTutorial.mapper.EndUserAdminViewMapper
import apariciomeli.tutorial.kotlinTutorial.model.Course
import apariciomeli.tutorial.kotlinTutorial.repo.CourseRepository
import apariciomeli.tutorial.kotlinTutorial.repo.EndUserRepository
import org.springframework.stereotype.Service

@Service
class CourseServiceImpl(
    private val courseRepository: CourseRepository,
    private val courseMapper: CourseMapper,
    private val endUserAdminViewMapper: EndUserAdminViewMapper,
    private val endUserRepository: EndUserRepository
) : CourseService {

    override fun createCourse(courseDTO: CourseDTO): ReturnCourseDTO {
    val course = courseMapper.toEntity(courseDTO)
      val savedCourse = courseRepository.save(course)
    return ReturnCourseDTO(id = savedCourse.id, name = savedCourse.name)
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

    override fun addUsersToCourse(mails:List<String>, courseId:Int): Int {
        for (mail in mails) {
            val userOptional = endUserRepository.findEndUserByEmailIgnoreCase(mail)
            if (userOptional.isPresent){
                val userPresent = userOptional.get()
                val course = courseRepository.findById(courseId).get()
                if (!userPresent.courses.contains(course)){
                    userPresent.courses.add(course)
                    endUserRepository.save(userPresent)
                }
            }
        }
        return 1;
    }

}
