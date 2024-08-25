package apariciomeli.tutorial.kotlinTutorial.service.comment

import apariciomeli.tutorial.kotlinTutorial.dto.comment.CommentDTO
import apariciomeli.tutorial.kotlinTutorial.dto.comment.CommentResponseDTO
import apariciomeli.tutorial.kotlinTutorial.dto.comment.GetCommentDTO
import apariciomeli.tutorial.kotlinTutorial.mapper.CommentMapper
import apariciomeli.tutorial.kotlinTutorial.mapper.CommentResponseMapper
import apariciomeli.tutorial.kotlinTutorial.mapper.EndUserAdminViewMapper
import apariciomeli.tutorial.kotlinTutorial.model.Comment
import apariciomeli.tutorial.kotlinTutorial.repo.CommentRepository
import apariciomeli.tutorial.kotlinTutorial.repo.EndUserRepository
import apariciomeli.tutorial.kotlinTutorial.repo.ModuleRepository
import org.springframework.stereotype.Service

@Service
class CommentServiceImpl(
    private val commentRepository: CommentRepository,
    private val commentMapper: CommentMapper,
    private val endUserRepository: EndUserRepository,
    private val moduleRepository: ModuleRepository,
    private val commentResponseMapper: CommentResponseMapper,
    private val endUserAdminViewMapper: EndUserAdminViewMapper,
) : CommentService {
  override fun createComment(commentDTO: CommentDTO): Comment {
    val comment = commentMapper.toEntity(commentDTO)
    return commentRepository.save(comment)
  }

  override fun getCommentsByUserId(userId: Int): List<GetCommentDTO> {
    val commentUserOptional = endUserRepository.findById(userId)
    val commentListUser = mutableListOf<GetCommentDTO>()
    if (commentUserOptional.isPresent) {
      val commentUser = commentUserOptional.get()
      commentRepository
          .getCommentsByUser(commentUser)
          .sortedBy { it.id }
          .forEach {
            commentListUser.add(
                GetCommentDTO(
                    id = it.id,
                    user = endUserAdminViewMapper.fromEntity(commentUser),
                    moduleId = it.module.id,
                    commentData = it.commentData))
          }
    }
    return commentListUser
  }

  override fun getCommentsByModuleId(moduleId: Int): List<GetCommentDTO> {
    val commentsModuleOptional = moduleRepository.findById(moduleId)
    val getCommentListByModule = mutableListOf<GetCommentDTO>()
    if (commentsModuleOptional.isPresent) {
      val commentsModule = commentsModuleOptional.get()
      commentRepository
          .getCommentsByModule(commentsModule)
          .sortedBy { it.id }
          .forEach {
            getCommentListByModule.add(
                GetCommentDTO(
                    id = it.id,
                    user = endUserAdminViewMapper.fromEntity(it.user),
                    moduleId = it.module.id,
                    commentData = it.commentData))
          }
    }
    return getCommentListByModule
  }

  override fun getCommentByUserIdAndModuleId(moduleId: Int): GetCommentDTO {
    val commentModuleOptional = moduleRepository.findById(moduleId)
    val commentUserOptional = endUserRepository.findById(1)
    if (commentUserOptional.isPresent && commentModuleOptional.isPresent) {
      val commentUserPresent = commentUserOptional.get()
      val commentModulePresent = commentModuleOptional.get()
      val commentOptional =
          commentRepository.getCommentByUserAndModule(commentUserPresent, commentModulePresent)
      return if (commentOptional.isPresent) {
        val commentPresent = commentOptional.get()
        GetCommentDTO(
            id = commentPresent.id,
            user = endUserAdminViewMapper.fromEntity(commentUserPresent),
            moduleId = moduleId,
            commentData = commentPresent.commentData)
      } else {
        GetCommentDTO(
            id = -1,
            user = endUserAdminViewMapper.fromEntity(commentUserPresent),
            moduleId = moduleId,
            commentData = "")
      }
    }
    throw Exception("User not found.")
  }

  override fun getAllComments(): List<CommentResponseDTO> {
    val commentListParsed = commentRepository.findAll()
    val returnList = mutableListOf<CommentResponseDTO>()
    commentListParsed
        .sortedBy { it.id }
        .forEach { returnList.add(commentResponseMapper.fromEntity(it)) }
    return returnList
  }
}
