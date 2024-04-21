package apariciomeli.tutorial.kotlinTutorial.repo

import apariciomeli.tutorial.kotlinTutorial.model.Comment
import apariciomeli.tutorial.kotlinTutorial.model.Course
import apariciomeli.tutorial.kotlinTutorial.model.EndUser
import apariciomeli.tutorial.kotlinTutorial.model.Module
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.CrudRepository
import java.util.*

interface CommentRepository: CrudRepository<Comment, Int>, JpaRepository<Comment,Int> {
    fun getCommentsByUser(endUser: EndUser): List<Comment>
    fun getCommentsByModule(module: Module): List<Comment>

    fun getCommentByUserAndModule(endUser: EndUser, module: Module): Optional<Comment>

}