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
            entity.id,
            entity.name,
            entity.email,
            entity.password
        )
    }

    override fun toEntity(domain: EndUserDTO): EndUser {
        return EndUser(
            domain.id,
            domain.name,
            domain.password,
            domain.email,
            java.util.Calendar.getInstance(),
            listOf()
        )
    }
}
