package apariciomeli.tutorial.kotlinTutorial.service.moduledata

import apariciomeli.tutorial.kotlinTutorial.model.SetGroup

interface SetGroupService {
  fun getGroupsByCourseId(courseId: Int): List<SetGroup>
}
