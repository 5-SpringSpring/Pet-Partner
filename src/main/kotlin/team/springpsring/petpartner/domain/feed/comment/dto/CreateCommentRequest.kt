package team.springpsring.petpartner.domain.feed.comment.dto

import java.time.LocalDateTime

class CreateCommentRequest (
    val body: String,
    val createdAt: LocalDateTime,
)