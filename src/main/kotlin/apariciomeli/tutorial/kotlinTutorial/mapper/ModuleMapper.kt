package apariciomeli.tutorial.kotlinTutorial.mapper

import apariciomeli.tutorial.kotlinTutorial.DTO.ModuleDTO
import apariciomeli.tutorial.kotlinTutorial.model.Module
import apariciomeli.tutorial.kotlinTutorial.repo.CourseRepository
import apariciomeli.tutorial.kotlinTutorial.service.course.CourseServiceImpl
import org.springframework.stereotype.Service

@Service
class ModuleMapper(
    private val courseServiceImpl: CourseServiceImpl,
    private val courseRepository: CourseRepository
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
            course = courseRepository.findById(domain.id).get(),
            question = domain.question
        )
    }
}