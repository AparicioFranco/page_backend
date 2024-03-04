package apariciomeli.tutorial.kotlinTutorial.service.course

import apariciomeli.tutorial.kotlinTutorial.DTO.EndUserCheckedDTO
import apariciomeli.tutorial.kotlinTutorial.DTO.EndUserDTO
import apariciomeli.tutorial.kotlinTutorial.DTO.EndUserLogInDTO
import apariciomeli.tutorial.kotlinTutorial.mapper.EndUserMapper
import apariciomeli.tutorial.kotlinTutorial.model.Course
import apariciomeli.tutorial.kotlinTutorial.model.EndUser
import apariciomeli.tutorial.kotlinTutorial.repo.CourseRepository
import apariciomeli.tutorial.kotlinTutorial.repo.EndUserRepository
import org.springframework.stereotype.Service

@Service
class EndUserServiceImpl(
    private val endUserRepository: EndUserRepository,
    private val endUserMapper: EndUserMapper,
    private val courseRepository: CourseRepository
): EndUserService {
    override fun createUser(endUserDTO: EndUserDTO): EndUser {
        val user = endUserMapper.toEntity(endUserDTO)
        return endUserRepository.save(user)
    }

    override fun findAllUsers(): List<EndUser> {
        return endUserRepository.findAll()
    }

    override fun findUserById(userId: Int): EndUser {
        return endUserRepository.findById(userId).get()
    }

    override fun addUserToCourse(userId: Int, courseId: Int): EndUser {
        val user = endUserRepository.findById(userId).get()
        val course = courseRepository.findById(courseId).get()

        user.courses.add(course)
//        return endUserRepository.save(user)
//        course.users.add(user)
//        courseRepository.save(course)
        return endUserRepository.save(user)
    }

    override fun getCoursesByUserId(userId: Int): List<Course> {
        val user = endUserRepository.findById(userId).get()
        println("Courses:")
        println(user.courses)
        return user.courses.toList()
    }

    override fun checkUser(user: EndUserLogInDTO): EndUserCheckedDTO {
        val checkUser = endUserRepository.findEndUserByEmail(user.email)
        if (checkUser.password == user.password){
            return EndUserCheckedDTO(id = checkUser.id, name = checkUser.name, email = checkUser.email, role = checkUser.role);
        }else{
            throw Exception("Username not found")
        }
    }
}