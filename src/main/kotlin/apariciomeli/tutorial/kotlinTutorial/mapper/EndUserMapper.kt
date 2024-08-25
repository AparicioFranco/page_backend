package apariciomeli.tutorial.kotlinTutorial.mapper

import apariciomeli.tutorial.kotlinTutorial.dto.user.EndUserDTO
import apariciomeli.tutorial.kotlinTutorial.model.EndUser
import org.springframework.stereotype.Service
import apariciomeli.tutorial.kotlinTutorial.model.Role

@Service
class EndUserMapper: Mapper<EndUserDTO, EndUser> {
    override fun fromEntity(entity: EndUser): EndUserDTO {
        return EndUserDTO(
            id = entity.id,
            name=  entity.name,
            email = entity.email,
            role = Role.valueOf(entity.role.name),
            password = entity.passw
        )
    }

    override fun toEntity(domain: EndUserDTO): EndUser {
        return EndUser(
            id = domain.id,
            name = domain.name,
            passw = domain.password,
            email = domain.email,
            role = domain.role,
            joinDate = java.util.Calendar.getInstance(),
            courses = mutableListOf()
        )
    }
}
