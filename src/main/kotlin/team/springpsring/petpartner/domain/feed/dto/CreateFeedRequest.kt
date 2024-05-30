package team.springpsring.petpartner.domain.feed.dto

import jakarta.validation.constraints.NotBlank
import java.time.LocalDateTime

data class CreateFeedRequest(
    val title: String,
    val body:String,
    val images : String,
    val category:Int,
    val created: LocalDateTime,
    val token:String
)
