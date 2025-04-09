package apariciomeli.tutorial.kotlinTutorial.dto.user

import apariciomeli.tutorial.kotlinTutorial.dto.comment.EndUserAdminViewDTO

data class CourseAndUsersDTO(
    val courseId: Int,
    val courseName: String,
    val users: List<EndUserAdminViewDTO>
)
