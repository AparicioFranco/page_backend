package apariciomeli.tutorial.kotlinTutorial.DTO

import apariciomeli.tutorial.kotlinTutorial.model.Course
import apariciomeli.tutorial.kotlinTutorial.model.Module

class GetUserByIdDTO (
    val id: Int,
    val name: String,
    val email: String,
    val role: String,
    val courses: MutableList<Course>,
    val modules: MutableList<Module>
)