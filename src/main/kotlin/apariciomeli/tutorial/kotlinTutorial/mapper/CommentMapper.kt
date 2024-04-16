package apariciomeli.tutorial.kotlinTutorial.mapper

import apariciomeli.tutorial.kotlinTutorial.DTO.CommentDTO
import apariciomeli.tutorial.kotlinTutorial.model.Comment
import apariciomeli.tutorial.kotlinTutorial.service.course.EndUserService
import apariciomeli.tutorial.kotlinTutorial.service.course.EndUserServiceImpl
import apariciomeli.tutorial.kotlinTutorial.service.module.ModuleServiceImpl
import org.springframework.stereotype.Service

@Service
class CommentMapper(
    private val endUserServiceImpl: EndUserServiceImpl,
    private val moduleServiceImpl: ModuleServiceImpl
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
            user = endUserServiceImpl.findUserById(domain.userId),
            module = moduleServiceImpl.findModuleById(domain.moduleId),
            commentData = domain.commentData
        )
    }

}