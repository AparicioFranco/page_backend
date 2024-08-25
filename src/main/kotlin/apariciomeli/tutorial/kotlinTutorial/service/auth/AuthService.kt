package apariciomeli.tutorial.kotlinTutorial.service.auth

import apariciomeli.tutorial.kotlinTutorial.controller.auth.AuthenticationRequest
import apariciomeli.tutorial.kotlinTutorial.controller.auth.AuthenticationResponse
import apariciomeli.tutorial.kotlinTutorial.controller.auth.RegisterRequest

interface AuthService {
  fun register(registerRequest: RegisterRequest): AuthenticationResponse

  fun authenticate(authenticationRequest: AuthenticationRequest): AuthenticationResponse
}
