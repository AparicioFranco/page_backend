package apariciomeli.tutorial.kotlinTutorial.DTO

data class CourseSendDTO(
    val id: Int,
    val name: String,
    val description: String,
    val startDate: CustomDateDTO
)