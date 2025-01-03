package apariciomeli.tutorial.kotlinTutorial.mapper

import apariciomeli.tutorial.kotlinTutorial.dto.module.ModuleDataTestDTO
import apariciomeli.tutorial.kotlinTutorial.model.ModuleData
import apariciomeli.tutorial.kotlinTutorial.service.module.ModuleServiceImpl
import org.springframework.stereotype.Service

@Service
class ModuleDataTestMapper(
    private val moduleServiceImpl: ModuleServiceImpl
): Mapper<ModuleDataTestDTO, ModuleData> {
    override fun fromEntity(entity: ModuleData): ModuleDataTestDTO {
        return ModuleDataTestDTO(
//            id = -1,
            moduleId = entity.module.id,
            title = entity.title,
            text = entity.text,
            video = entity.video,
//            audio = entity.audio,
            link = entity.link,
            linkText = entity.linkText,
            file = entity.file,
//            audioFile = entity.audio,
            audioText = entity.audio
        )
    }

    override fun toEntity(domain: ModuleDataTestDTO): ModuleData {
        return ModuleData(
            module = moduleServiceImpl.findModuleById(domain.moduleId),
            title = domain.title,
            text = domain.text.orEmpty(),
            video = domain.video.orEmpty(),
            audio = domain.audioText.orEmpty(),
            link = domain.link.orEmpty(),
            linkText = domain.linkText.orEmpty(),
            file = domain.file.orEmpty(),
        )
    }

}