package apariciomeli.tutorial.kotlinTutorial.mapper

import apariciomeli.tutorial.kotlinTutorial.dto.user.CreateEndUserDTO
import apariciomeli.tutorial.kotlinTutorial.dto.user.EndUserDTO
import apariciomeli.tutorial.kotlinTutorial.model.EndUser
import org.springframework.stereotype.Service
import apariciomeli.tutorial.kotlinTutorial.model.Role
import org.springframework.security.crypto.password.PasswordEncoder

@Service
class CreateEndUserMapper(
    val passwordEncoder: PasswordEncoder
): Mapper<CreateEndUserDTO, EndUser> {
    override fun fromEntity(entity: EndUser): CreateEndUserDTO {
        return CreateEndUserDTO(id=entity.id, email = entity.email, password = entity.passw)
    }

    override fun toEntity(domain: CreateEndUserDTO): EndUser {
        return EndUser(
            id = 0,
            email = domain.email,
            passw = passwordEncoder.encode(domain.password),
            name = "Usuario",
            role = Role.USER,
            joinDate = java.util.Calendar.getInstance(),
            courses = mutableListOf()
        )
    }
}

