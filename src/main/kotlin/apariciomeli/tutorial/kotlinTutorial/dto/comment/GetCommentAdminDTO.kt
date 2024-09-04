package apariciomeli.tutorial.kotlinTutorial.dto.comment

class GetCommentAdminDTO (
    var id: Int,
    var user: EndUserAdminViewDTO,
    var module: ModuleAdminViewDTO,
    var commentData: String
)
