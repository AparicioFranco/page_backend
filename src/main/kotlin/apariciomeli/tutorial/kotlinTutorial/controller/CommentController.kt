package apariciomeli.tutorial.kotlinTutorial.controller

import apariciomeli.tutorial.kotlinTutorial.dto.comment.CommentDTO
import apariciomeli.tutorial.kotlinTutorial.dto.comment.CommentResponseDTO
import apariciomeli.tutorial.kotlinTutorial.dto.comment.GetCommentDTO
import apariciomeli.tutorial.kotlinTutorial.model.Comment
import apariciomeli.tutorial.kotlinTutorial.service.comment.CommentService
import org.springframework.web.bind.annotation.*

@CrossOrigin("*")
@RestController
@RequestMapping("/api/comment")
class CommentController(
    private val commentService: CommentService
) {

    @PostMapping("/public/add")
    fun createComment(@RequestBody commentDTO: CommentDTO): Comment {
        return commentService.createComment(commentDTO)
    }

    @GetMapping("/private/user/{courseId}")
    fun getCommentsByUserId(@PathVariable courseId: Int): List<GetCommentDTO> {
        return commentService.getCommentsByUserId(courseId)
    }

    @GetMapping("/private/module/{moduleId}")
    fun getCommentsByModuleId(@PathVariable moduleId: Int): List<GetCommentDTO> {
        return commentService.getCommentsByModuleId(moduleId)
    }

    @GetMapping("/public/own/{moduleId}")
    fun getCommentByUserIdAndModuleId(@PathVariable moduleId: Int): GetCommentDTO {
        return commentService.getCommentByUserIdAndModuleId(moduleId)
    }

    @GetMapping("/private/all")
    fun getAllComments(): List<CommentResponseDTO> {
        return commentService.getAllComments()
    }




}