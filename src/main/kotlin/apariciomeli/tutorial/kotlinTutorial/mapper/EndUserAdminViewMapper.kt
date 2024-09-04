package apariciomeli.tutorial.kotlinTutorial.mapper

import apariciomeli.tutorial.kotlinTutorial.dto.comment.EndUserAdminViewDTO
import apariciomeli.tutorial.kotlinTutorial.model.EndUser
import org.springframework.stereotype.Service

@Service
class EndUserAdminViewMapper: Mapper<EndUserAdminViewDTO, EndUser> {
    override fun fromEntity(entity: EndUser): EndUserAdminViewDTO {
        return EndUserAdminViewDTO(
            id=entity.id,
            name=entity.name,
            email=entity.email,
        )
    }

    override fun toEntity(domain: EndUserAdminViewDTO): EndUser {
        TODO("Not yet implemented")
    }

}