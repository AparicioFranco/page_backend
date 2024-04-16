package apariciomeli.tutorial.kotlinTutorial.service.comment

import apariciomeli.tutorial.kotlinTutorial.DTO.CommentDTO
import apariciomeli.tutorial.kotlinTutorial.DTO.CommentResponseDTO
import apariciomeli.tutorial.kotlinTutorial.model.Comment

interface CommentService {

    fun createComment(commentDTO: CommentDTO): Comment

    fun getCommentsByUserId(userId: Int): List<Comment>

    fun getCommentsByModuleId(moduleId: Int): List<Comment>

    fun getCommentByUserIdAndModuleId(userId: Int, moduleId: Int): Comment

    fun getAllComments(): List<CommentResponseDTO>

}