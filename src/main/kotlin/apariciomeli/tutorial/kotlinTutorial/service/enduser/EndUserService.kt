package apariciomeli.tutorial.kotlinTutorial.service.course

import apariciomeli.tutorial.kotlinTutorial.DTO.*
import apariciomeli.tutorial.kotlinTutorial.model.Comment
import apariciomeli.tutorial.kotlinTutorial.model.Course
import apariciomeli.tutorial.kotlinTutorial.model.EndUser
import apariciomeli.tutorial.kotlinTutorial.model.Module

interface EndUserService {
    fun createUser(endUserDTO: EndUserDTO): EndUser
    fun findAllUsers(): List<EndUserAdminViewDTO>
    fun findUserById(userId: Int): EndUser
    fun addUserToCourse(userId: Int, courseId: Int): EndUser
    fun getCoursesByUserId(userId: Int): List<Course>
    fun checkUser(user: EndUserLogInDTO) : EndUserCheckedDTO
    fun changePassword(passwordDTO: ChangePasswordDTO): EndUser
    fun addModuleToModuleReadList(userId: Int, moduleId: Int): List<Module>
    fun getModulesCompletedByUserId(userId: Int): List<Module>
}