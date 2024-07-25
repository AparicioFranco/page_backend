package apariciomeli.tutorial.kotlinTutorial.service.auth

import apariciomeli.tutorial.kotlinTutorial.DTO.CommentDTO
import apariciomeli.tutorial.kotlinTutorial.DTO.CommentResponseDTO
import apariciomeli.tutorial.kotlinTutorial.DTO.GetCommentDTO
import apariciomeli.tutorial.kotlinTutorial.controller.auth.AuthenticationRequest
import apariciomeli.tutorial.kotlinTutorial.controller.auth.AuthenticationResponse
import apariciomeli.tutorial.kotlinTutorial.controller.auth.RegisterRequest
import apariciomeli.tutorial.kotlinTutorial.model.Comment

interface AuthService {
    fun register(registerRequest: RegisterRequest) : AuthenticationResponse

    fun authenticate(authenticationRequest: AuthenticationRequest) : AuthenticationResponse

}