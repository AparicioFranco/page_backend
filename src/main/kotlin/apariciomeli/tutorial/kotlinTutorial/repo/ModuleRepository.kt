package apariciomeli.tutorial.kotlinTutorial.repo

import apariciomeli.tutorial.kotlinTutorial.model.Module
import apariciomeli.tutorial.kotlinTutorial.model.SetGroup
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.CrudRepository

interface ModuleRepository : CrudRepository<Module, Int>, JpaRepository<Module, Int> {
  //    fun findAllByCourse(course:Course): List<Module>
  //    fun findAllByCourseAndLocked(course: Course, locked: Boolean): List<Module>
  fun findAllByGroup(group: SetGroup): List<Module>

  fun findAllByGroupAndLocked(group: SetGroup, locked: Boolean): List<Module>
}
