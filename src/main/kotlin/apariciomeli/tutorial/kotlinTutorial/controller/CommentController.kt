package apariciomeli.tutorial.kotlinTutorial.controller

import apariciomeli.tutorial.kotlinTutorial.DTO.CommentDTO
import apariciomeli.tutorial.kotlinTutorial.DTO.CommentResponseDTO
import apariciomeli.tutorial.kotlinTutorial.DTO.CourseDTO
import apariciomeli.tutorial.kotlinTutorial.DTO.GetCommentDTO
import apariciomeli.tutorial.kotlinTutorial.model.Comment
import apariciomeli.tutorial.kotlinTutorial.model.Course
import apariciomeli.tutorial.kotlinTutorial.service.comment.CommentService
import org.springframework.web.bind.annotation.*

@CrossOrigin("*")
@RestController
@RequestMapping("/api/comment")
class CommentController(
    private val commentService: CommentService
) {

    @PostMapping()
    fun createComment(@RequestBody commentDTO: CommentDTO): Comment {
        return commentService.createComment(commentDTO)
    }

    @GetMapping("/user/{courseId}")
    fun getCommentsByUserId(@PathVariable courseId: Int): List<GetCommentDTO> {
        return commentService.getCommentsByUserId(courseId)
    }

    @GetMapping("/module/{moduleId}")
    fun getCommentsByModuleId(@PathVariable moduleId: Int): List<GetCommentDTO> {
        return commentService.getCommentsByModuleId(moduleId)
    }

    @GetMapping("/user/{userId}/{moduleId}")
    fun getCommentByUserIdAndModuleId(@PathVariable userId: Int, @PathVariable moduleId: Int): GetCommentDTO {
        return commentService.getCommentByUserIdAndModuleId(userId,moduleId)
    }

    @GetMapping("/all")
    fun getAllComments(): List<CommentResponseDTO> {
        return commentService.getAllComments()
    }




}