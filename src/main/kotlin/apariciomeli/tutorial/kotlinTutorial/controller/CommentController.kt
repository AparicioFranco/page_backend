package apariciomeli.tutorial.kotlinTutorial.controller

import apariciomeli.tutorial.kotlinTutorial.dto.comment.*
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
    fun createComment(@RequestHeader("Authorization") bearerToken: String, @RequestBody commentDTO: CommentDTO): ReturnCommentDTO {
        return commentService.createComment(bearerToken, commentDTO)
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

    @GetMapping("/private/course/{courseId}")
    fun getAllCommentsByCourseId(@PathVariable courseId: Int): List<GetCommentAdminDTO> {
        return commentService.getAllCommentsByCourseId(courseId)
    }

    @GetMapping("/private/all")
    fun getAllComments(): List<CommentResponseDTO> {
        return commentService.getAllComments()
    }




}