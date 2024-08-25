package apariciomeli.tutorial.kotlinTutorial.dto.comment

import apariciomeli.tutorial.kotlinTutorial.dto.user.EndUserAdminViewDTO

class GetCommentDTO (
    var id: Int,
    var user: EndUserAdminViewDTO,
    var moduleId: Int,
    var commentData: String
)
