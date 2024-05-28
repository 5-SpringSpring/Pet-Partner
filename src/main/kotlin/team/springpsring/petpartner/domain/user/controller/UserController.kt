package team.springpsring.petpartner.domain.user.controller


import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import team.springpsring.petpartner.domain.user.dto.LogInUserRequest
import team.springpsring.petpartner.domain.user.dto.SignUpUserRequest
import team.springpsring.petpartner.domain.user.service.UserService

@RequestMapping("/users")
@RestController
class UserController(private val userService: UserService) {
    @PostMapping("/signup")
    fun signUpUser(@RequestBody userRequest: SignUpUserRequest)
    : ResponseEntity<Any> {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(userService.signUpUser(userRequest))
    }

    @PostMapping("/login")
    fun loginUser(@RequestBody userRequest: LogInUserRequest)
    : ResponseEntity<Any> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(userService.logInUser(userRequest))
    }

    @GetMapping("/{token}")
    fun getUser(@RequestParam("token") token: String): ResponseEntity<Any> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(userService.getUserIdFromToken(token))
    }
}
