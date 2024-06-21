package apariciomeli.tutorial.kotlinTutorial.service.course

import apariciomeli.tutorial.kotlinTutorial.DTO.*
import apariciomeli.tutorial.kotlinTutorial.model.Course
import apariciomeli.tutorial.kotlinTutorial.model.EndUser
import apariciomeli.tutorial.kotlinTutorial.model.Module

interface EndUserService {
    fun createUser(endUserDTO: EndUserDTO): EndUser
    fun findAllUsers(): List<EndUserAdminViewDTO>
    fun findUserById(userId: Int): GetUserByIdDTO
    fun addUserToCourse(userId: Int, courseId: Int): EndUser
    fun getCoursesByUserId(token: String): List<Course>
    fun checkUser(user: EndUserLogInDTO) : EndUserCheckedDTO
    fun changePassword(bearerToken: String, passwordDTO: ChangePasswordDTO): EndUser
    fun addModuleToModuleReadList(bearerToken:String, moduleId: Int): List<Module>
    fun getModulesCompletedByUserToken(token: String): List<Module>
}