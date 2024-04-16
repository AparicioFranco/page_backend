package apariciomeli.tutorial.kotlinTutorial.mapper

import apariciomeli.tutorial.kotlinTutorial.DTO.ModuleDataDTO
import apariciomeli.tutorial.kotlinTutorial.model.ModuleData
import apariciomeli.tutorial.kotlinTutorial.service.module.ModuleServiceImpl
import org.springframework.stereotype.Service

@Service
class ModuleDataMapper(
    private val moduleServiceImpl: ModuleServiceImpl
): Mapper<ModuleDataDTO, ModuleData> {
    override fun fromEntity(entity: ModuleData): ModuleDataDTO {
        return ModuleDataDTO(
            id = entity.id,
            moduleId = entity.module.id,
            title = entity.title,
            text = entity.text,
            video = entity.video,
            audio = entity.audio
        )
    }

    override fun toEntity(domain: ModuleDataDTO): ModuleData {
        return ModuleData(
            id = domain.id,
            module = moduleServiceImpl.findModuleById(domain.moduleId),
            title = domain.title,
            text = domain.text,
            video = domain.video,
            audio = domain.audio
        )
    }


}