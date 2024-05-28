package team.springpsring.petpartner.domain.feed.dto

import team.springpsring.petpartner.domain.feed.comment.entity.Comment
import java.time.LocalDateTime

data class FeedResponse (
    val id:Long,
    val name:String,
    val title: String,
    val body:String,
    val images : String,
    val category:Int,
    val loves:Int,
    val views:Int,
    val created: LocalDateTime
)