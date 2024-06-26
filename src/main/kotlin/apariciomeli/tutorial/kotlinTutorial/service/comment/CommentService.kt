package apariciomeli.tutorial.kotlinTutorial.service.comment

import apariciomeli.tutorial.kotlinTutorial.DTO.CommentDTO
import apariciomeli.tutorial.kotlinTutorial.DTO.CommentResponseDTO
import apariciomeli.tutorial.kotlinTutorial.DTO.GetCommentDTO
import apariciomeli.tutorial.kotlinTutorial.model.Comment

interface CommentService {

    fun createComment(commentDTO: CommentDTO): Comment

    fun getCommentsByUserId(userId: Int): List<GetCommentDTO>

    fun getCommentsByModuleId(moduleId: Int): List<GetCommentDTO>

    fun getCommentByUserIdAndModuleId(userId: Int, moduleId: Int): GetCommentDTO

    fun getAllComments(): List<CommentResponseDTO>

}