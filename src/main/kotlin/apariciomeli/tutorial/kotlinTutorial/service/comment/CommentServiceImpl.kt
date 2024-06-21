package apariciomeli.tutorial.kotlinTutorial.service.comment

import apariciomeli.tutorial.kotlinTutorial.DTO.CommentDTO
import apariciomeli.tutorial.kotlinTutorial.DTO.CommentResponseDTO
import apariciomeli.tutorial.kotlinTutorial.DTO.GetCommentDTO
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
): CommentService {
    override fun createComment(commentDTO: CommentDTO): Comment {
        val comment = commentMapper.toEntity(commentDTO)
        return commentRepository.save(comment)
    }

    override fun getCommentsByUserId(userId: Int): List<GetCommentDTO> {
        val commentUser = endUserRepository.findById(userId).get()
        val commentListUser = mutableListOf<GetCommentDTO>()
        commentRepository.getCommentsByUser(commentUser).sortedBy { it.id }.forEach{commentListUser.add(GetCommentDTO(id = it.id, user = endUserAdminViewMapper.fromEntity(commentUser), moduleId = it.module.id, commentData = it.commentData)) }
        return commentListUser
    }

    override fun getCommentsByModuleId(moduleId: Int): List<GetCommentDTO> {
        val commentsModule = moduleRepository.findById(moduleId).get()
        val getCommentListByModule = mutableListOf<GetCommentDTO>()
        commentRepository.getCommentsByModule(commentsModule).sortedBy { it.id }.forEach { getCommentListByModule.add(GetCommentDTO(id = it.id, user = endUserAdminViewMapper.fromEntity(it.user), moduleId = it.module.id, commentData = it.commentData)) }
        return getCommentListByModule
    }

    override fun getCommentByUserIdAndModuleId(moduleId: Int): GetCommentDTO {
        val commentModuleOptional = moduleRepository.findById(moduleId)
        //TODO
        val commentUserOptional = endUserRepository.findById(1)
        if (commentUserOptional.isPresent && commentModuleOptional.isPresent){
            val commentObject = commentRepository.getCommentByUserAndModule(commentUserOptional.get(),commentModuleOptional.get())
            return if (commentObject.isPresent) {
                GetCommentDTO(
                    id = commentObject.get().id,
                    user = endUserAdminViewMapper.fromEntity(commentUserOptional.get()),
                    moduleId = moduleId,
                    commentData = commentObject.get().commentData )
            }else{
                GetCommentDTO(id = -1,
                    user = endUserAdminViewMapper.fromEntity(commentUserOptional.get()),
                    moduleId = moduleId,
                    commentData = "")
            }
        }
        throw Exception("User not found.")
    }

    override fun getAllComments(): List<CommentResponseDTO> {
        val commentListParsed = commentRepository.findAll()
        val returnList = mutableListOf<CommentResponseDTO>()
        commentListParsed.sortedBy { it.id }.forEach {returnList.add(commentResponseMapper.fromEntity(it)) }
        return returnList
    }


}