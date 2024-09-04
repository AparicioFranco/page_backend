package apariciomeli.tutorial.kotlinTutorial.repo

import apariciomeli.tutorial.kotlinTutorial.model.Course
import apariciomeli.tutorial.kotlinTutorial.model.SetGroup
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.CrudRepository

interface SetGroupRepository: CrudRepository<SetGroup, Int>, JpaRepository<SetGroup, Int> {
    fun findAllByCourse(course: Course): List<SetGroup>
}