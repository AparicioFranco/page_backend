package apariciomeli.tutorial.kotlinTutorial.service.auth

import apariciomeli.tutorial.kotlinTutorial.config.JwtService
import apariciomeli.tutorial.kotlinTutorial.controller.auth.AuthenticationRequest
import apariciomeli.tutorial.kotlinTutorial.controller.auth.AuthenticationResponse
import apariciomeli.tutorial.kotlinTutorial.controller.auth.RegisterRequest
import apariciomeli.tutorial.kotlinTutorial.dto.user.EndUserDTO
import apariciomeli.tutorial.kotlinTutorial.dto.user.RegisterEndUserDTO
import apariciomeli.tutorial.kotlinTutorial.mapper.EndUserMapper
import apariciomeli.tutorial.kotlinTutorial.model.EndUser
import apariciomeli.tutorial.kotlinTutorial.model.Role
import apariciomeli.tutorial.kotlinTutorial.repo.EndUserRepository
import apariciomeli.tutorial.kotlinTutorial.service.course.EndUserService
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthServiceImpl(
    private val endUserRepository: EndUserRepository,
    private val endUserService: EndUserService,
    private val passwordEncoder: PasswordEncoder,
    private val endUserMapper: EndUserMapper,
    private val jwtService: JwtService,
    private val authenticationManager: AuthenticationManager
) : AuthService {

//  override fun register(registerRequest: RegisterRequest): AuthenticationResponse {
//    val endUserDTORegistration =
//        RegisterEndUserDTO(
//            email = registerRequest.email,
//            name = "Usuario",
//            password = passwordEncoder.encode(registerRequest.password),
//            role = Role.USER)
//    val endUserFinal = endUserMapper.toEntity(endUserDTORegistration)
//    endUserRepository.save(endUserFinal)
//    val jwtToken = jwtService.generateToken(endUserFinal)
//    return AuthenticationResponse(jwtToken, endUserFinal.role.name)
//  }

    override fun register(endUserDTO: EndUserDTO): AuthenticationResponse {
        val email = endUserDTO.email
        val endUserOptional = endUserRepository.findEndUserByEmailIgnoreCase(email)
        if (!endUserOptional.isPresent){
            println("Entered")
            val endUserDTORegistration = endUserMapper.toEntity(endUserDTO)
            endUserRepository.save(endUserDTORegistration)
            val jwtToken = jwtService.generateToken(endUserDTORegistration)
            return AuthenticationResponse(jwtToken, endUserDTORegistration.role.name)
        }
        println("Not entered")
        throw Exception("User is present.")
    }

    override fun authenticate(authenticationRequest: AuthenticationRequest): AuthenticationResponse {
    authenticationManager.authenticate(
        UsernamePasswordAuthenticationToken(
            authenticationRequest.email, authenticationRequest.password))
    val userInfo =
        endUserRepository.findEndUserByEmailIgnoreCase(authenticationRequest.email).orElseThrow()
    val jwtToken = jwtService.generateToken(userInfo)
    val sendRole = if (userInfo.role.name === "ADMIN") "admin" else "user"
    return AuthenticationResponse(jwtToken, sendRole)
  }
}
