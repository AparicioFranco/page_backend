package apariciomeli.tutorial.kotlinTutorial.controller

import apariciomeli.tutorial.kotlinTutorial.controller.auth.AuthenticationRequest
import apariciomeli.tutorial.kotlinTutorial.controller.auth.AuthenticationResponse
import apariciomeli.tutorial.kotlinTutorial.controller.auth.RegisterRequest
import apariciomeli.tutorial.kotlinTutorial.dto.user.EndUserDTO
import apariciomeli.tutorial.kotlinTutorial.dto.user.RegisterEndUserDTO
import apariciomeli.tutorial.kotlinTutorial.dto.user.ReturnEndUserDTO
import apariciomeli.tutorial.kotlinTutorial.dto.user.UserEmailDTO
import apariciomeli.tutorial.kotlinTutorial.model.EndUser
import apariciomeli.tutorial.kotlinTutorial.service.auth.AuthService
import apariciomeli.tutorial.kotlinTutorial.service.course.EndUserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
class AuthenticationController(
    private val authService: AuthService,
    private val endUserService: EndUserService
) {

  @PostMapping("/private/register")
  fun register(@RequestBody endUserDTO: EndUserDTO): ResponseEntity<AuthenticationResponse> {
    return ResponseEntity.ok(authService.register(endUserDTO))
  }

  @PostMapping("/public/authenticate")
  fun authenticate(
      @RequestBody request: AuthenticationRequest
  ): ResponseEntity<AuthenticationResponse> {
    return ResponseEntity.ok(authService.authenticate(request))
  }

  @PostMapping("/public/reset/password")
  fun resetPassword(@RequestBody userEmail: UserEmailDTO): ReturnEndUserDTO {
    return endUserService.resetUserPassword(userEmail)
  }
}
