package team.springpsring.petpartner.domain.user.dto

data class UpdateUserNameRequest (
    val username:String,
    val password:String,
    val user : GetUserInfoRequest
)