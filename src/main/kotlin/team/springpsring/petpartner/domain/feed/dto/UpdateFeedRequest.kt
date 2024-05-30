package team.springpsring.petpartner.domain.feed.dto

import java.time.LocalDateTime

data class UpdateFeedRequest (
    val title: String,
    val body:String,
    val images : String,
    val category:Int,
    val created: LocalDateTime,
    val token:String
)