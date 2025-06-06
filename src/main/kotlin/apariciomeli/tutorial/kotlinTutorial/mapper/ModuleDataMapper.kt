package apariciomeli.tutorial.kotlinTutorial.mapper

import apariciomeli.tutorial.kotlinTutorial.dto.module.ModuleDataDTO
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
            text = entity.text.orEmpty(),
            video = entity.video.orEmpty(),
            audio = entity.audio,
            link = entity.link.orEmpty(),
            linkText = entity.linkText.orEmpty(),
            file = entity.file.orEmpty(),
        )
    }

    override fun toEntity(domain: ModuleDataDTO): ModuleData {
        return ModuleData(
            id = domain.id,
            module = moduleServiceImpl.findModuleById(domain.moduleId),
            title = domain.title,
            text = domain.text,
            video = domain.video,
            audio = domain.audio,
            link = domain.link,
            linkText = domain.linkText,
            file = domain.file
        )
    }


}