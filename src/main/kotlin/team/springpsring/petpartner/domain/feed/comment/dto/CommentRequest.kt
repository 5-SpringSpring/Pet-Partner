package team.springpsring.petpartner.domain.feed.comment.dto

import java.time.LocalDateTime

class CommentRequest (
    val body: String,
    val createdAt: LocalDateTime,
)