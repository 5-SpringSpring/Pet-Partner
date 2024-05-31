package team.springpsring.petpartner.domain.feed.dto

import team.springpsring.petpartner.domain.feed.entity.CategoryType
import team.springpsring.petpartner.domain.user.dto.GetUserInfoRequest
import java.time.LocalDateTime

data class FeedRequest(
    val title: String,
    val body:String,
    val images : String,
    val category:CategoryType,
    val created: LocalDateTime,
    val user: GetUserInfoRequest
)
