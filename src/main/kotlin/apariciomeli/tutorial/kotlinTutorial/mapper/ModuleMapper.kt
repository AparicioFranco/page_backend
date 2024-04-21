package apariciomeli.tutorial.kotlinTutorial.mapper

import apariciomeli.tutorial.kotlinTutorial.DTO.ModuleDTO
import apariciomeli.tutorial.kotlinTutorial.model.Module
import apariciomeli.tutorial.kotlinTutorial.service.comment.CommentServiceImpl
import apariciomeli.tutorial.kotlinTutorial.service.course.CourseServiceImpl
import org.springframework.stereotype.Service

@Service
class ModuleMapper(
    private val courseServiceImpl: CourseServiceImpl
): Mapper<ModuleDTO, Module> {

    override fun fromEntity(entity: Module): ModuleDTO {
        return ModuleDTO(
            id = entity.id,
            courseId = entity.course.id,
            name = entity.name,
            locked = true,
            question = entity.question
        )
    }

    override fun toEntity(domain: ModuleDTO): Module {
        return Module(
            id = domain.id,
            name = domain.name,
            locked = true,
            course = courseServiceImpl.findCourseById(domain.courseId),
            question = domain.question
        )
    }
}