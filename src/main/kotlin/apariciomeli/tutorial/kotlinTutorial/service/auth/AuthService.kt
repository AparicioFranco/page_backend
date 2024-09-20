package apariciomeli.tutorial.kotlinTutorial.service.auth

import apariciomeli.tutorial.kotlinTutorial.controller.auth.AuthenticationRequest
import apariciomeli.tutorial.kotlinTutorial.controller.auth.AuthenticationResponse
import apariciomeli.tutorial.kotlinTutorial.controller.auth.RegisterRequest
import apariciomeli.tutorial.kotlinTutorial.dto.user.EndUserDTO
import apariciomeli.tutorial.kotlinTutorial.dto.user.RegisterEndUserDTO
import apariciomeli.tutorial.kotlinTutorial.dto.user.ReturnEndUserDTO
import apariciomeli.tutorial.kotlinTutorial.model.EndUser

interface AuthService {
  fun register(endUserDTO: EndUserDTO): AuthenticationResponse

  fun authenticate(authenticationRequest: AuthenticationRequest): AuthenticationResponse
}
