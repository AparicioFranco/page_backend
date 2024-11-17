package apariciomeli.tutorial.kotlinTutorial.service.modulegroup

import apariciomeli.tutorial.kotlinTutorial.dto.setgroup.GroupDTO
import apariciomeli.tutorial.kotlinTutorial.mapper.GroupMapper
import apariciomeli.tutorial.kotlinTutorial.model.SetGroup
import apariciomeli.tutorial.kotlinTutorial.repo.CourseRepository
import apariciomeli.tutorial.kotlinTutorial.repo.SetGroupRepository
import apariciomeli.tutorial.kotlinTutorial.service.moduledata.SetGroupService
import org.springframework.stereotype.Service

@Service
class SetGroupServiceImpl(
    private val setGroupRepository: SetGroupRepository,
    private val courseRepository: CourseRepository,
    private val groupMapper: GroupMapper
) : SetGroupService {

    override fun getGroupsByCourseId(courseId: Int): List<SetGroup> {
    val courseOptional = courseRepository.findById(courseId)
    if (courseOptional.isPresent) {
      val course = courseOptional.get()
      return setGroupRepository.findAllByCourse(course)
    }
    throw Exception("Course not found")
  }

    override fun createGroup(setGroup: GroupDTO): GroupDTO {
        val newGroup = groupMapper.toEntity(setGroup)
        val savedCourse = setGroupRepository.save(newGroup)
        return GroupDTO(savedCourse.id,savedCourse.course.id,savedCourse.name)
    }
}
