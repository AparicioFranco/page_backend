package apariciomeli.tutorial.kotlinTutorial.mapper

import apariciomeli.tutorial.kotlinTutorial.dto.comment.CommentModule
import apariciomeli.tutorial.kotlinTutorial.dto.comment.CommentResponseDTO
import apariciomeli.tutorial.kotlinTutorial.dto.comment.CommentUser
import apariciomeli.tutorial.kotlinTutorial.model.Comment
import apariciomeli.tutorial.kotlinTutorial.repo.EndUserRepository
import apariciomeli.tutorial.kotlinTutorial.repo.ModuleRepository
import org.springframework.stereotype.Service

@Service
class CommentResponseMapper(
    private val endUserRepository: EndUserRepository,
    private val moduleRepository: ModuleRepository
): Mapper<CommentResponseDTO,Comment> {
    override fun fromEntity(entity: Comment): CommentResponseDTO {
        return CommentResponseDTO(
            id = entity.id,
            user = CommentUser(entity.user.id, entity.user.name),
            module = CommentModule(entity.module.id, entity.module.name),
            commentData = entity.commentData
        )
    }

    override fun toEntity(domain: CommentResponseDTO): Comment {
        return Comment(
            id = domain.id,
            user = endUserRepository.findById(domain.user.id).get(),
            module = moduleRepository.findById(domain.module.id).get(),
            commentData = domain.commentData
        )
    }

}