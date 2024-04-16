package apariciomeli.tutorial.kotlinTutorial.service.course

import apariciomeli.tutorial.kotlinTutorial.DTO.CourseDTO
import apariciomeli.tutorial.kotlinTutorial.mapper.CourseMapper
import apariciomeli.tutorial.kotlinTutorial.model.Course
import apariciomeli.tutorial.kotlinTutorial.model.EndUser
import apariciomeli.tutorial.kotlinTutorial.repo.CourseRepository
import apariciomeli.tutorial.kotlinTutorial.service.comment.CommentService
import org.springframework.stereotype.Service

@Service
class CourseServiceImpl(
    private val courseRepository: CourseRepository,
    private val courseMapper: CourseMapper
): CourseService {

    override fun createCourse(courseDTO: CourseDTO): Course {
        val course = courseMapper.toEntity(courseDTO)
        return courseRepository.save(course)
    }

    override fun findCourseById(courseId: Int): Course {
        return courseRepository.findById(courseId).orElseThrow()
    }

    override fun findAllCourses(): List<Course> {
        return courseRepository.findAll()
    }

    override fun getUsersByCourseId(courseId: Int): List<EndUser> {
        val course = courseRepository.findById(courseId).get()
        println(course.users)
        return course.users.toList()
    }


}