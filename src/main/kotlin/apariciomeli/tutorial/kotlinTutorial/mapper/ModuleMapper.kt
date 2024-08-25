package apariciomeli.tutorial.kotlinTutorial.mapper

import apariciomeli.tutorial.kotlinTutorial.dto.module.ModuleDTO
import apariciomeli.tutorial.kotlinTutorial.model.Module
import apariciomeli.tutorial.kotlinTutorial.repo.CourseRepository
import apariciomeli.tutorial.kotlinTutorial.repo.SetGroupRepository
import apariciomeli.tutorial.kotlinTutorial.service.course.CourseServiceImpl
import org.springframework.stereotype.Service

@Service
class ModuleMapper(
    private val setGroupRepository: SetGroupRepository
): Mapper<ModuleDTO, Module> {

    override fun fromEntity(entity: Module): ModuleDTO {
        return ModuleDTO(
            id = entity.id,
            setGroupId = entity.group.id,
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
            group = setGroupRepository.findById(domain.id).get(),
            question = domain.question
        )
    }
}