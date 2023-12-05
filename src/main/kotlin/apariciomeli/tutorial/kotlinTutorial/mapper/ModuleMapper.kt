package apariciomeli.tutorial.kotlinTutorial.mapper

import apariciomeli.tutorial.kotlinTutorial.DTO.ModuleDTO
import apariciomeli.tutorial.kotlinTutorial.model.Module
import apariciomeli.tutorial.kotlinTutorial.service.course.CourseServiceImpl
import apariciomeli.tutorial.kotlinTutorial.service.course.EndUserServiceImpl
import org.springframework.stereotype.Service

@Service
class ModuleMapper(
    private val courseServiceImpl: CourseServiceImpl
): Mapper<ModuleDTO, Module> {

    override fun fromEntity(entity: Module): ModuleDTO {
        return ModuleDTO(
            entity.id,
            entity.course.id,
            entity.name,
            entity.video,
            entity.text
        )
    }

    override fun toEntity(domain: ModuleDTO): Module {
        return Module(
            domain.id,
            domain.name,
            courseServiceImpl.findCourseById(domain.courseId),
            domain.video,
            domain.text
        )
    }
}