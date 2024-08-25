package apariciomeli.tutorial.kotlinTutorial.service.course

import apariciomeli.tutorial.kotlinTutorial.config.JwtService
import apariciomeli.tutorial.kotlinTutorial.dto.user.*
import apariciomeli.tutorial.kotlinTutorial.mapper.EndUserAdminViewMapper
import apariciomeli.tutorial.kotlinTutorial.mapper.EndUserMapper
import apariciomeli.tutorial.kotlinTutorial.model.Course
import apariciomeli.tutorial.kotlinTutorial.model.EndUser
import apariciomeli.tutorial.kotlinTutorial.model.Module
import apariciomeli.tutorial.kotlinTutorial.model.Role
import apariciomeli.tutorial.kotlinTutorial.repo.CourseRepository
import apariciomeli.tutorial.kotlinTutorial.repo.EndUserRepository
import apariciomeli.tutorial.kotlinTutorial.repo.ModuleRepository
import apariciomeli.tutorial.kotlinTutorial.repo.SetGroupRepository
import apariciomeli.tutorial.kotlinTutorial.service.enduser.EmailSenderService
import apariciomeli.tutorial.kotlinTutorial.service.module.ModuleServiceImpl
import java.util.concurrent.ThreadLocalRandom
import kotlin.streams.asSequence
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class EndUserServiceImpl(
    private val endUserRepository: EndUserRepository,
    private val endUserMapper: EndUserMapper,
    private val endUserAdminViewMapper: EndUserAdminViewMapper,
    private val courseRepository: CourseRepository,
    private val moduleRepository: ModuleRepository,
    private val jwtService: JwtService,
    private val moduleServiceImpl: ModuleServiceImpl,
    private val emailSenderService: EmailSenderService,
    private val setGroupRepository: SetGroupRepository,
) : EndUserService {
  private val passwordEncoder = BCryptPasswordEncoder()
  val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')

  fun generateRandomPassword(): String {
    return ThreadLocalRandom.current()
        .ints(12, 0, charPool.size)
        .asSequence()
        .map(charPool::get)
        .joinToString("")
  }

  override fun createUser(endUserDTO: EndUserDTO): EndUser {
    endUserDTO.password = passwordEncoder.encode(endUserDTO.password)
    endUserDTO.role = Role.USER
    val user = endUserMapper.toEntity(endUserDTO)
    return endUserRepository.save(user)
  }

  override fun findAllUsers(): List<EndUserAdminViewDTO> {
    val endUserAdminViewList = mutableListOf<EndUserAdminViewDTO>()
    endUserRepository.findAll().forEach {
      endUserAdminViewList.add(endUserAdminViewMapper.fromEntity(it))
    }
    return endUserAdminViewList
  }

  override fun findUserById(userId: Int): GetUserByIdDTO {
    val userOptional = endUserRepository.findById(userId)
    if (userOptional.isPresent) {
      val userPresent = userOptional.get()
      return GetUserByIdDTO(
          id = userPresent.id,
          name = userPresent.name,
          email = userPresent.email,
          role = userPresent.role.name,
          courses = userPresent.courses,
          modules = userPresent.modules)
    }
    throw Exception("User not found")
  }

  override fun addUserToCourse(userId: Int, courseId: Int): EndUser {
    val userOptional = endUserRepository.findById(userId)
    val courseOptional = courseRepository.findById(courseId)
    if (userOptional.isPresent && courseOptional.isPresent) {
      val userPresent = userOptional.get()
      val coursePresent = courseOptional.get()
      userPresent.courses.add(coursePresent)
      return endUserRepository.save(userPresent)
    }
    throw Exception("User not found")
  }

  override fun getCoursesByUserId(token: String): List<Course> {
    val email = jwtService.extractUsernameFromToken(token = token)
    val user = endUserRepository.findEndUserByEmailIgnoreCase(email)
    if (user.isPresent) {
      return user.get().courses
    }
    throw Exception("User not found.")
  }

  override fun checkUser(user: EndUserLogInDTO): EndUserCheckedDTO {
    val checkUser = endUserRepository.findEndUserByEmailIgnoreCase(user.email)
    if (checkUser.isPresent) {
      val checkUserPresent: EndUser = checkUser.get()
      if (passwordEncoder.matches(user.password, checkUserPresent.passw)) {
        return EndUserCheckedDTO(
            id = checkUserPresent.id,
            name = checkUserPresent.name,
            email = checkUserPresent.email,
            role = checkUserPresent.role.name)
      }
    }
    throw Exception("Username not found")
  }

  override fun changePassword(bearerToken: String, passwordDTO: ChangePasswordDTO): EndUser {
    val userEmail = jwtService.extractUsernameFromToken(bearerToken)
    val user = endUserRepository.findEndUserByEmailIgnoreCase(userEmail)
    if (user.isPresent) {
      if (passwordEncoder.matches(passwordDTO.oldPassword, user.get().password)) {
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
    if (user.isPresent && module.isPresent) {
      val userPresent = user.get()
      val modulePresent = module.get()
      userPresent.modules.add(modulePresent)
      endUserRepository.save(userPresent)
      return userPresent.modules
    }
    throw Exception("User not found.")
  }

  override fun getModulesCompletedByUserToken(token: String): List<Module> {
    val email = jwtService.extractUsernameFromToken(token = token)
    val userOptional = endUserRepository.findEndUserByEmailIgnoreCase(email)
    if (userOptional.isPresent) {
      val userPresent = userOptional.get()
      return userPresent.modules
    }
    throw Exception("User not found.")
  }

  override fun getCompletedModulesForCalendar(token: String, courseId: Int): List<Int> {
    val userEmail = jwtService.extractUsernameFromToken(token)
    val userOptional = endUserRepository.findEndUserByEmailIgnoreCase(userEmail)
    val availableModules =
        moduleServiceImpl.getAvailableModulesByCourseId(courseId).sortedBy { it.id }
    val courseOptional = courseRepository.findById(courseId)
    if (userOptional.isPresent && courseOptional.isPresent) {
      val userPresent = userOptional.get()
      val coursePresent = courseOptional.get()
      val availableGroupsInCourse = setGroupRepository.findAllByCourse(coursePresent)
      val completedModules =
          userPresent.modules
              .filter { availableGroupsInCourse.contains(it.group) }
              .sortedBy { it.id }
      val calendarList = mutableListOf<Int>()
      for (module in availableModules) {
        if (module.name == "Instrucciones" || module.name == "Bonus") {
          continue
        }
        if (completedModules.contains(module)) {
          calendarList.add(1)
        } else {
          calendarList.add(0)
        }
      }
      return calendarList
    }
    throw Exception("User not found.")
  }

  override fun resetUserPassword(userEmail: UserEmailDTO): EndUser {
    val email = userEmail.userEmail
    val userOptional = endUserRepository.findEndUserByEmailIgnoreCase(email)
    if (userOptional.isPresent) {
      val user = userOptional.get()
      val newPassword = generateRandomPassword()
      val finalUser = user.copy(passw = passwordEncoder.encode(newPassword))
      emailSenderService.sendEMail(newPassword, email)
      return endUserRepository.save(finalUser)
    }
    throw Exception("User not found.")
  }

  override fun sendEmail(userEmail: UserEmailDTO) {
    emailSenderService.sendEMail("apa1234", userEmail.userEmail)
  }
}
