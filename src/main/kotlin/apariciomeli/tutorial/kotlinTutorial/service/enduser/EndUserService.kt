package apariciomeli.tutorial.kotlinTutorial.service.course

import apariciomeli.tutorial.kotlinTutorial.dto.comment.EndUserAdminViewDTO
import apariciomeli.tutorial.kotlinTutorial.dto.user.*
import apariciomeli.tutorial.kotlinTutorial.model.Course
import apariciomeli.tutorial.kotlinTutorial.model.Module
import org.springframework.http.ResponseEntity

interface EndUserService {
  fun createUser(endUserDTO: EndUserDTO): ReturnEndUserDTO

  fun createUserFromList(listOfUsers: List<String>): Int

  fun createUserAndAddToCourse(email: String, courseId: Int): Int

  fun findAllUsers(): List<EndUserAdminViewDTO>

  fun getUsersByCourseId(courseId: Int): List<EndUserAdminViewDTO>

  fun findUserById(userId: Int): GetUserByIdDTO

  fun addUserToCourse(userId: Int, courseId: Int): ReturnEndUserDTO

  fun getCoursesByUserId(token: String): List<Course>

  fun checkUser(user: EndUserLogInDTO): EndUserCheckedDTO

  fun changePassword(bearerToken: String, passwordDTO: ChangePasswordDTO): ReturnEndUserDTO

  fun addModuleToModuleReadList(bearerToken: String, moduleId: Int): List<Module>

  fun getModulesCompletedByUserToken(token: String): List<Module>

  fun getCompletedModulesForCalendar(token: String, courseId: Int): List<Int>

  fun resetUserPassword(userEmail: UserEmailDTO): ReturnEndUserDTO

  fun sendEmail(userEmail: UserEmailDTO)

  fun checkUserInCourse(token: String, courseId: Int): ResponseEntity<Int>
}
