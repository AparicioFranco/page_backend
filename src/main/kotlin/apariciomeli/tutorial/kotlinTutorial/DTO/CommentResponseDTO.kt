package apariciomeli.tutorial.kotlinTutorial.DTO

class CommentResponseDTO (
    var id: Int,
    var user: CommentUser,
    var module: CommentModule,
    var commentData: String
)

class CommentUser(
    val id: Int,
    val name: String
)

class CommentModule(
    val id: Int,
    val name: String
)

