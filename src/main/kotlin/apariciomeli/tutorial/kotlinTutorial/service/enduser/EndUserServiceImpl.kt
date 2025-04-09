package apariciomeli.tutorial.kotlinTutorial.service.course

import apariciomeli.tutorial.kotlinTutorial.config.JwtService
import apariciomeli.tutorial.kotlinTutorial.dto.comment.EndUserAdminViewDTO
import apariciomeli.tutorial.kotlinTutorial.dto.user.*
import apariciomeli.tutorial.kotlinTutorial.mapper.CreateEndUserMapper
import apariciomeli.tutorial.kotlinTutorial.mapper.EndUserAdminViewMapper
import apariciomeli.tutorial.kotlinTutorial.mapper.EndUserMapper
import apariciomeli.tutorial.kotlinTutorial.model.Course
import apariciomeli.tutorial.kotlinTutorial.model.EndUser
import apariciomeli.tutorial.kotlinTutorial.model.Module
import apariciomeli.tutorial.kotlinTutorial.repo.CourseRepository
import apariciomeli.tutorial.kotlinTutorial.repo.EndUserRepository
import apariciomeli.tutorial.kotlinTutorial.repo.ModuleRepository
import apariciomeli.tutorial.kotlinTutorial.repo.SetGroupRepository
import apariciomeli.tutorial.kotlinTutorial.service.enduser.EmailSenderService
import apariciomeli.tutorial.kotlinTutorial.service.module.ModuleServiceImpl
import java.util.concurrent.ThreadLocalRandom
import kotlin.streams.asSequence
import org.springframework.http.ResponseEntity
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
    private val createEndUserMapper: CreateEndUserMapper
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

  override fun createUser(endUserDTO: EndUserDTO): ReturnEndUserDTO {
    if (endUserRepository.findEndUserByEmailIgnoreCase(endUserDTO.email).isEmpty) {
      endUserDTO.password = passwordEncoder.encode(endUserDTO.password)
      val user = endUserMapper.toEntity(endUserDTO)
      val savedUser = endUserRepository.save(user)
      return ReturnEndUserDTO(id = savedUser.id, email = savedUser.email)
    }
    return ReturnEndUserDTO(id = 0, email = "Ya existe.")
  }

  override fun createUserFromList(listOfUsers: List<String>): Int {
    for (email in listOfUsers) {
      if (endUserRepository.findEndUserByEmailIgnoreCase(email).isEmpty) {
        val password = generateRandomPassword()
        val firstUser = CreateEndUserDTO(id = 0, email = email, password = password)
        emailSenderService.sendCreationEmail(password, email)
        endUserRepository.save(createEndUserMapper.toEntity(firstUser))
      }
    }
    return 1
  }

  override fun createUserAndAddToCourse(email: String, courseId: Int): Int {
    val course = courseRepository.findById(courseId).get()
    val userOptional = endUserRepository.findEndUserByEmailIgnoreCase(email)
    if (userOptional.isEmpty) {
      val password = generateRandomPassword()
      emailSenderService.sendCreationEmail(password, email)
      val newUser =
          createEndUserMapper.toEntity(CreateEndUserDTO(id = 0, email = email, password = password))
      newUser.courses.add(course)
      endUserRepository.save(newUser)
    } else {
      val existingUser = userOptional.get()
      existingUser.courses.add(course)
      emailSenderService.sendPurchasedAudiobook(existingUser.email)
      endUserRepository.save(existingUser)
    }
    return 1
  }

  override fun findAllUsers(): List<CourseAndUsersDTO> {
    val courses = courseRepository.findAll().sortedBy { it.id }
    return courses.map { course ->
      CourseAndUsersDTO(
          courseId = course.id,
          courseName = course.name,
          users =
              course.users.map { user ->
                EndUserAdminViewDTO(id = user.id, name = user.name, email = user.email)
              })
    }
  }

  override fun getUsersByCourseId(courseId: Int): List<EndUserAdminViewDTO> {
    val courseOptional = courseRepository.findById(courseId)
    if (courseOptional.isPresent) {
      val coursePresent = courseOptional.get()
      val endUserAdminViewList = mutableListOf<EndUserAdminViewDTO>()
      endUserRepository.findAllByCoursesContaining(coursePresent).forEach {
        endUserAdminViewList.add(endUserAdminViewMapper.fromEntity(it))
      }
      return endUserAdminViewList.sortedBy { it.id }
    }
    throw Exception("Course not found")
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

  override fun addUserToCourse(userId: Int, courseId: Int): ReturnEndUserDTO {
    val userOptional = endUserRepository.findById(userId)
    val courseOptional = courseRepository.findById(courseId)
    if (userOptional.isPresent && courseOptional.isPresent) {
      val userPresent = userOptional.get()
      val coursePresent = courseOptional.get()
      userPresent.courses.add(coursePresent)
      val savedUser = endUserRepository.save(userPresent)
      return ReturnEndUserDTO(id = savedUser.id, email = savedUser.email)
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

  override fun changePassword(
      bearerToken: String,
      passwordDTO: ChangePasswordDTO
  ): ReturnEndUserDTO {
    val userEmail = jwtService.extractUsernameFromToken(bearerToken)
    val user = endUserRepository.findEndUserByEmailIgnoreCase(userEmail)
    if (user.isPresent) {
      if (passwordEncoder.matches(passwordDTO.oldPassword, user.get().password)) {
        val finalUser = user.get().copy(passw = passwordEncoder.encode(passwordDTO.newPassword))
        val savedUser = endUserRepository.save(finalUser)
        return ReturnEndUserDTO(id = savedUser.id, email = savedUser.email)
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

  override fun resetUserPassword(userEmail: UserEmailDTO): ReturnEndUserDTO {
    val email = userEmail.userEmail
    val userOptional = endUserRepository.findEndUserByEmailIgnoreCase(email)
    if (userOptional.isPresent) {
      val user = userOptional.get()
      val newPassword = generateRandomPassword()
      val finalUser = user.copy(passw = passwordEncoder.encode(newPassword))
      emailSenderService.sendRecoverEmail(newPassword, email)
      val savedUser = endUserRepository.save(finalUser)
      return ReturnEndUserDTO(id = savedUser.id, email = savedUser.email)
    }
    throw Exception("User not found.")
  }

  override fun sendEmail(userEmail: UserEmailDTO) {
    emailSenderService.sendRecoverEmail("apa1234", userEmail.userEmail)
  }

  override fun checkUserInCourse(token: String, courseId: Int): ResponseEntity<Int> {
    val userEmail = jwtService.extractUsernameFromToken(token)
    val courseOptional = courseRepository.findById(courseId)
    val userOptional = endUserRepository.findEndUserByEmailIgnoreCase(userEmail)
    if (courseOptional.isPresent && userOptional.isPresent) {
      val coursePresent = courseOptional.get()
      val userPresent = userOptional.get()
      return if (userPresent.courses.contains(coursePresent)) {
        ResponseEntity.ok(1)
      } else {
        ResponseEntity.badRequest().build()
      }
    }
    throw Exception("User or course not found.")
  }
}
