package apariciomeli.tutorial.kotlinTutorial.repo

import apariciomeli.tutorial.kotlinTutorial.model.Course
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.CrudRepository

interface CourseRepository: CrudRepository<Course, Int>, JpaRepository<Course,Int> {
}