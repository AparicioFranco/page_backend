package apariciomeli.tutorial.kotlinTutorial.mapper

import apariciomeli.tutorial.kotlinTutorial.DTO.CourseDTO
import apariciomeli.tutorial.kotlinTutorial.model.Course
import org.springframework.stereotype.Service
import apariciomeli.tutorial.kotlinTutorial.model.Module

@Service
class CourseMapper: Mapper<CourseDTO, Course> {
    override fun fromEntity(entity: Course): CourseDTO {
        return CourseDTO(
            entity.id,
            entity.name
        )
    }

    override fun toEntity(domain: CourseDTO): Course {
        return Course(
            domain.id,
            domain.name,
            listOf()
        )
    }
}
