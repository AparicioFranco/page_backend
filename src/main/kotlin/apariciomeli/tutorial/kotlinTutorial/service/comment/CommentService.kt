package apariciomeli.tutorial.kotlinTutorial.service.comment

import apariciomeli.tutorial.kotlinTutorial.dto.comment.*
import apariciomeli.tutorial.kotlinTutorial.model.Comment

interface CommentService {
  fun createComment(bearerToken: String, commentDTO: CommentDTO): ReturnCommentDTO

  fun getCommentsByUserId(userId: Int): List<GetCommentDTO>

  fun getCommentsByModuleId(moduleId: Int): List<GetCommentDTO>

  fun getCommentByUserIdAndModuleId(moduleId: Int): GetCommentDTO

  fun getAllCommentsByCourseId(courseId: Int): List<GetCommentAdminDTO>

  fun getCommentsByModuleIdAdmin(moduleId: Int): List<GetCommentAdminDTO>

  fun getAllComments(): List<CommentResponseDTO>

}
