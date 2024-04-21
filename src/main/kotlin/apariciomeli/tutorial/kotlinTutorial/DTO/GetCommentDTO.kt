package apariciomeli.tutorial.kotlinTutorial.DTO

class GetCommentDTO (
    var id: Int,
    var user: EndUserAdminViewDTO,
    var moduleId: Int,
    var commentData: String
)
