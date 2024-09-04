package apariciomeli.tutorial.kotlinTutorial.mapper

import apariciomeli.tutorial.kotlinTutorial.dto.course.CourseDTO
import apariciomeli.tutorial.kotlinTutorial.model.Course
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class CourseMapper: Mapper<CourseDTO, Course> {
    override fun fromEntity(entity: Course): CourseDTO {
        return CourseDTO(
            id = entity.id,
            name = entity.name,
            description = entity.description
        )
    }

    override fun toEntity(domain: CourseDTO): Course {
        return Course(
            id = domain.id,
            name = domain.name,
            description = domain.description,
            users = mutableListOf(),
            startDate = LocalDate.now()
        )
    }
}
