package apariciomeli.tutorial.kotlinTutorial.service.comment

import apariciomeli.tutorial.kotlinTutorial.DTO.CommentDTO
import apariciomeli.tutorial.kotlinTutorial.DTO.CommentResponseDTO
import apariciomeli.tutorial.kotlinTutorial.mapper.CommentMapper
import apariciomeli.tutorial.kotlinTutorial.mapper.CommentResponseMapper
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
    private val commentResponseMapper: CommentResponseMapper
): CommentService {
    override fun createComment(commentDTO: CommentDTO): Comment {
        val comment = commentMapper.toEntity(commentDTO)
        return commentRepository.save(comment)
    }

    override fun getCommentsByUserId(userId: Int): List<Comment> {
        val user = endUserRepository.findById(userId).get()
        return commentRepository.getCommentsByUser(user)
    }

    override fun getCommentsByModuleId(moduleId: Int): List<Comment> {
        val module = moduleRepository.findById(moduleId).get()
        return commentRepository.getCommentsByModule(module)
    }

    override fun getCommentByUserIdAndModuleId(userId: Int, moduleId: Int): Comment {
        val module = moduleRepository.findById(moduleId).get()
        val user = endUserRepository.findById(userId).get()
        return commentRepository.getCommentByUserAndModule(user,module)
    }

    override fun getAllComments(): List<CommentResponseDTO> {
        val commentListParsed = commentRepository.findAll()
        val returnList = mutableListOf<CommentResponseDTO>()
        for (comment in commentListParsed) {
            returnList.add(commentResponseMapper.fromEntity(comment))
        }
        return returnList
    }


}