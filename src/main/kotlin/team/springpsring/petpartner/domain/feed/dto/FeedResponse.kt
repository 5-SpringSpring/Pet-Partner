package team.springpsring.petpartner.domain.feed.dto

import team.springpsring.petpartner.domain.feed.comment.dto.CommentResponse
import java.time.LocalDateTime

data class FeedResponse(
    val id:Long,
    val name:String,
    val title: String,
    val body:String,
    val images: String,
    val category:Int,
    val views:Int,
    val created: LocalDateTime,
    val comments: List<CommentResponse>,
)