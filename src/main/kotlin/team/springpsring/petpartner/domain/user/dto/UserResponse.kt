package team.springpsring.petpartner.domain.user.dto

data class UserResponse(
    val loginId: String,
    //val password: String,
    val email: String,
    val username: String,
)