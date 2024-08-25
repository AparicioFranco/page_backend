package apariciomeli.tutorial.kotlinTutorial.service.comment

import apariciomeli.tutorial.kotlinTutorial.dto.comment.CommentDTO
import apariciomeli.tutorial.kotlinTutorial.dto.comment.CommentResponseDTO
import apariciomeli.tutorial.kotlinTutorial.dto.comment.GetCommentDTO
import apariciomeli.tutorial.kotlinTutorial.model.Comment

interface CommentService {
  fun createComment(commentDTO: CommentDTO): Comment

  fun getCommentsByUserId(userId: Int): List<GetCommentDTO>

  fun getCommentsByModuleId(moduleId: Int): List<GetCommentDTO>

  fun getCommentByUserIdAndModuleId(moduleId: Int): GetCommentDTO

  fun getAllComments(): List<CommentResponseDTO>
}
