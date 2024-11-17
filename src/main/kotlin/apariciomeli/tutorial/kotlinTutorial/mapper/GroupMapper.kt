package apariciomeli.tutorial.kotlinTutorial.mapper


import apariciomeli.tutorial.kotlinTutorial.dto.setgroup.GroupDTO
import apariciomeli.tutorial.kotlinTutorial.model.SetGroup
import apariciomeli.tutorial.kotlinTutorial.repo.CourseRepository
import org.springframework.stereotype.Service

@Service
class GroupMapper(
    private val courseRepository: CourseRepository
): Mapper<GroupDTO, SetGroup> {
    override fun fromEntity(entity: SetGroup): GroupDTO {
        return GroupDTO(
            id = entity.id,
            courseId = entity.course.id,
            name = entity.name
        )
    }

    override fun toEntity(domain: GroupDTO): SetGroup {
        return SetGroup(
            id = domain.id,
            course = courseRepository.findById(domain.courseId).get(),
            name = domain.name
        )
    }
}
