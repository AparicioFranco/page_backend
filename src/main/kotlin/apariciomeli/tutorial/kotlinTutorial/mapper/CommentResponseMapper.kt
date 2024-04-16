package apariciomeli.tutorial.kotlinTutorial.mapper

import apariciomeli.tutorial.kotlinTutorial.DTO.*
import apariciomeli.tutorial.kotlinTutorial.model.Comment
import apariciomeli.tutorial.kotlinTutorial.service.course.EndUserService
import apariciomeli.tutorial.kotlinTutorial.service.course.EndUserServiceImpl
import apariciomeli.tutorial.kotlinTutorial.service.module.ModuleServiceImpl
import org.springframework.stereotype.Service

@Service
class CommentResponseMapper(
    private val endUserServiceImpl: EndUserServiceImpl,
    private val moduleServiceImpl: ModuleServiceImpl
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
            user = endUserServiceImpl.findUserById(domain.user.id),
            module = moduleServiceImpl.findModuleById(domain.module.id),
            commentData = domain.commentData
        )
    }

}