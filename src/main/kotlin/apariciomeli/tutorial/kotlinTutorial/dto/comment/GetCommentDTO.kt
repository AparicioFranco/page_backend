package apariciomeli.tutorial.kotlinTutorial.dto.comment

class GetCommentDTO (
    var id: Int,
    var user: EndUserAdminViewDTO,
    var moduleId: Int,
    var commentData: String
)
