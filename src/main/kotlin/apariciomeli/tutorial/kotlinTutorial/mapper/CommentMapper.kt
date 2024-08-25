package apariciomeli.tutorial.kotlinTutorial.mapper

import apariciomeli.tutorial.kotlinTutorial.dto.comment.CommentDTO
import apariciomeli.tutorial.kotlinTutorial.model.Comment
import apariciomeli.tutorial.kotlinTutorial.repo.EndUserRepository
import apariciomeli.tutorial.kotlinTutorial.repo.ModuleRepository
import apariciomeli.tutorial.kotlinTutorial.service.course.EndUserServiceImpl
import apariciomeli.tutorial.kotlinTutorial.service.module.ModuleServiceImpl
import org.springframework.stereotype.Service

@Service
class CommentMapper(
    private val endUserServiceImpl: EndUserServiceImpl,
    private val moduleServiceImpl: ModuleServiceImpl,
    private val endUserRepository: EndUserRepository,
    private val moduleRepository: ModuleRepository
): Mapper<CommentDTO,Comment> {
    override fun fromEntity(entity: Comment): CommentDTO {
        return CommentDTO(
            id = entity.id,
            userId = entity.user.id,
            moduleId = entity.module.id,
            commentData = entity.commentData
        )
    }

    override fun toEntity(domain: CommentDTO): Comment {
        return Comment(
            id = domain.id,
            user = endUserRepository.findById(domain.userId).get(),
            module = moduleRepository.findById(domain.moduleId).get(),
            commentData = domain.commentData
        )
    }

}