package apariciomeli.tutorial.kotlinTutorial.dto.comment

class CommentResponseDTO (
    var id: Int,
    var user: CommentUser,
    var module: CommentModule,
    var commentData: String
)

