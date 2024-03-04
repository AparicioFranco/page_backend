package apariciomeli.tutorial.kotlinTutorial.mapper

import apariciomeli.tutorial.kotlinTutorial.DTO.CourseDTO
import apariciomeli.tutorial.kotlinTutorial.DTO.EndUserDTO
import apariciomeli.tutorial.kotlinTutorial.model.Course
import apariciomeli.tutorial.kotlinTutorial.model.EndUser
import org.springframework.stereotype.Service
import apariciomeli.tutorial.kotlinTutorial.model.Module
import java.util.Date

@Service
class EndUserMapper: Mapper<EndUserDTO, EndUser> {
    override fun fromEntity(entity: EndUser): EndUserDTO {
        return EndUserDTO(
            id = entity.id,
            name=  entity.name,
            email = entity.email,
            role = entity.role,
            password = entity.password,
        )
    }

    override fun toEntity(domain: EndUserDTO): EndUser {
        return EndUser(
            id = domain.id,
            name = domain.name,
            password = domain.password,
            email = domain.email,
            role = domain.role,
            joinDate = java.util.Calendar.getInstance(),
            courses = mutableListOf()
        )
    }
}
