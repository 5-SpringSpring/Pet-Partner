package team.springpsring.petpartner.domain.feed.dto

import jakarta.validation.constraints.NotBlank
import team.springpsring.petpartner.domain.feed.entity.CategoryType
import java.time.LocalDateTime

data class CreateFeedRequest(
    val title: String,
    val body:String,
    val images : String,
    val category:CategoryType,
    val created: LocalDateTime,
    val token:String
)
