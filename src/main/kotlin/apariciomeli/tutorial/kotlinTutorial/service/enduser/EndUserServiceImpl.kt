package apariciomeli.tutorial.kotlinTutorial.service.course

import apariciomeli.tutorial.kotlinTutorial.DTO.*
import apariciomeli.tutorial.kotlinTutorial.config.JwtService
import apariciomeli.tutorial.kotlinTutorial.mapper.EndUserAdminViewMapper
import apariciomeli.tutorial.kotlinTutorial.mapper.EndUserMapper
import apariciomeli.tutorial.kotlinTutorial.model.Course
import apariciomeli.tutorial.kotlinTutorial.model.EndUser
import apariciomeli.tutorial.kotlinTutorial.model.Module
import apariciomeli.tutorial.kotlinTutorial.model.Role
import apariciomeli.tutorial.kotlinTutorial.repo.CourseRepository
import apariciomeli.tutorial.kotlinTutorial.repo.EndUserRepository
import apariciomeli.tutorial.kotlinTutorial.repo.ModuleRepository
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class EndUserServiceImpl(
    private val endUserRepository: EndUserRepository,
    private val endUserMapper: EndUserMapper,
    private val endUserAdminViewMapper: EndUserAdminViewMapper,
    private val courseRepository: CourseRepository,
    private val moduleRepository: ModuleRepository,
    private val jwtService: JwtService
): EndUserService {
    private val passwordEncoder = BCryptPasswordEncoder()

    override fun createUser(endUserDTO: EndUserDTO): EndUser {
        endUserDTO.password = passwordEncoder.encode(endUserDTO.password)
        endUserDTO.role = Role.USER
        val user = endUserMapper.toEntity(endUserDTO)
        return endUserRepository.save(user)
    }

    override fun findAllUsers(): List<EndUserAdminViewDTO> {
        val endUserAdminViewList = mutableListOf<EndUserAdminViewDTO>()
        endUserRepository.findAll().forEach { endUserAdminViewList.add(endUserAdminViewMapper.fromEntity(it)) }
        return endUserAdminViewList
    }

    override fun findUserById(userId: Int): GetUserByIdDTO {
        val user = endUserRepository.findById(userId).get()
        return GetUserByIdDTO(id = user.id, name = user.name, email = user.email, role = user.role.name, courses = user.courses, modules = user.modules)
    }

    override fun addUserToCourse(userId: Int, courseId: Int): EndUser {
        val user = endUserRepository.findById(userId).get()
        val course = courseRepository.findById(courseId).get()
        user.courses.add(course)
        return endUserRepository.save(user)
    }

    override fun getCoursesByUserId(token: String): List<Course> {
        val email = jwtService.extractUsernameFromToken(token = token)
        val user = endUserRepository.findEndUserByEmailIgnoreCase(email)
        if (user.isPresent){
            return user.get().courses
        }
        throw Exception("User not found.")
    }

    override fun checkUser(user: EndUserLogInDTO): EndUserCheckedDTO {
        val checkUser = endUserRepository.findEndUserByEmailIgnoreCase(user.email)
        if (checkUser.isPresent) {
            val checkUserPresent: EndUser = checkUser.get()
            if (passwordEncoder.matches(user.password,checkUserPresent.passw)){
                return EndUserCheckedDTO(id = checkUserPresent.id, name = checkUserPresent.name, email = checkUserPresent.email, role = checkUserPresent.role.name)
            }
        }
        throw Exception("Username not found")
    }

    override fun changePassword(bearerToken: String, passwordDTO: ChangePasswordDTO): EndUser {
        val userEmail = jwtService.extractUsernameFromToken(bearerToken)
        val user = endUserRepository.findEndUserByEmailIgnoreCase(userEmail)
        if (user.isPresent){
            if (passwordEncoder.matches(passwordDTO.oldPassword, user.get().password)){
                val finalUser = user.get().copy(passw = passwordEncoder.encode(passwordDTO.newPassword))
                return endUserRepository.save(finalUser)
            }
            throw Exception("Passwords don't match")
        }
        throw Exception("User not found.")
    }

    override fun addModuleToModuleReadList(bearerToken: String, moduleId: Int): List<Module> {
        val userEmail = jwtService.extractUsernameFromToken(bearerToken)
        val user = endUserRepository.findEndUserByEmailIgnoreCase(userEmail)
        val module = moduleRepository.findById(moduleId)
        if (user.isPresent && module.isPresent){
            user.get().modules.add(module.get())
            endUserRepository.save(user.get())
            return user.get().modules
        }
        throw Exception("User not found.")
    }

    override fun getModulesCompletedByUserToken(token: String): List<Module> {
        val email = jwtService.extractUsernameFromToken(token = token)
        val user = endUserRepository.findEndUserByEmailIgnoreCase(email)
        if (user.isPresent){
            return user.get().modules
        }
        throw Exception("User not found.")
    }

}