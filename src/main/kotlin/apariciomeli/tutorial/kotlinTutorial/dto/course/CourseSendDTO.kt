package apariciomeli.tutorial.kotlinTutorial.dto.course

data class CourseSendDTO(
    val id: Int,
    val name: String,
    val description: String,
    val startDate: CustomDateDTO
)