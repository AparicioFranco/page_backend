package apariciomeli.tutorial.kotlinTutorial.service.course

import apariciomeli.tutorial.kotlinTutorial.DTO.*
import apariciomeli.tutorial.kotlinTutorial.mapper.EndUserAdminViewMapper
import apariciomeli.tutorial.kotlinTutorial.mapper.EndUserMapper
import apariciomeli.tutorial.kotlinTutorial.model.Comment
import apariciomeli.tutorial.kotlinTutorial.model.Course
import apariciomeli.tutorial.kotlinTutorial.model.EndUser
import apariciomeli.tutorial.kotlinTutorial.model.Module
import apariciomeli.tutorial.kotlinTutorial.repo.CommentRepository
import apariciomeli.tutorial.kotlinTutorial.repo.CourseRepository
import apariciomeli.tutorial.kotlinTutorial.repo.EndUserRepository
import apariciomeli.tutorial.kotlinTutorial.repo.ModuleRepository
import org.springframework.stereotype.Service
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Service
class EndUserServiceImpl(
    private val endUserRepository: EndUserRepository,
    private val endUserMapper: EndUserMapper,
    private val endUserAdminViewMapper: EndUserAdminViewMapper,
    private val courseRepository: CourseRepository,
    private val moduleRepository: ModuleRepository,
    private val commentRepository: CommentRepository,
): EndUserService {
    private val passwordEncoder = BCryptPasswordEncoder()

    override fun createUser(endUserDTO: EndUserDTO): EndUser {
        endUserDTO.password = passwordEncoder.encode(endUserDTO.password)
        val user = endUserMapper.toEntity(endUserDTO)
        return endUserRepository.save(user)
    }

    override fun findAllUsers(): List<EndUserAdminViewDTO> {
        val endUserAdminViewList = mutableListOf<EndUserAdminViewDTO>()
        endUserRepository.findAll().forEach { endUserAdminViewList.add(endUserAdminViewMapper.fromEntity(it)) }
        return endUserAdminViewList
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
        return user.courses
    }

    override fun checkUser(user: EndUserLogInDTO): EndUserCheckedDTO {
        val checkUser = endUserRepository.findEndUserByEmail(user.email)
        if (passwordEncoder.matches(user.password,checkUser.password)){
            return EndUserCheckedDTO(id = checkUser.id, name = checkUser.name, email = checkUser.email, role = checkUser.role)
        }else{
            throw Exception("Username not found")
        }
    }

    override fun changePassword(passwordDTO: ChangePasswordDTO): EndUser {
        val user = endUserRepository.findById(passwordDTO.userId).get()
        if (passwordEncoder.matches(passwordDTO.oldPassword, user.password)){
            val finalUser = user.copy(password = passwordEncoder.encode(passwordDTO.newPassword))
            return endUserRepository.save(finalUser)
        }
        throw Exception("Passwords don't match")
    }

    override fun addModuleToModuleReadList(userId: Int, moduleId: Int): List<Module> {
        val user = endUserRepository.findById(userId).get()
        val module = moduleRepository.findById(moduleId).get()
        user.modules.add(module)
        endUserRepository.save(user)
        return user.modules
    }

    override fun getModulesCompletedByUserId(userId: Int): List<Module> {
        val user = endUserRepository.findById(userId).get()
        return user.modules
    }

}