package apariciomeli.tutorial.kotlinTutorial.service.course

import apariciomeli.tutorial.kotlinTutorial.DTO.ChangePasswordDTO
import apariciomeli.tutorial.kotlinTutorial.DTO.EndUserCheckedDTO
import apariciomeli.tutorial.kotlinTutorial.DTO.EndUserDTO
import apariciomeli.tutorial.kotlinTutorial.DTO.EndUserLogInDTO
import apariciomeli.tutorial.kotlinTutorial.model.Course
import apariciomeli.tutorial.kotlinTutorial.model.EndUser

interface EndUserService {
    fun createUser(endUserDTO: EndUserDTO): EndUser
    fun findAllUsers(): List<EndUser>
    fun findUserById(userId: Int): EndUser
    fun addUserToCourse(userId: Int, courseId: Int): EndUser
    fun getCoursesByUserId(userId: Int): List<Course>
    fun checkUser(user: EndUserLogInDTO) : EndUserCheckedDTO

    fun changePassword(passwordDTO: ChangePasswordDTO): EndUser
}