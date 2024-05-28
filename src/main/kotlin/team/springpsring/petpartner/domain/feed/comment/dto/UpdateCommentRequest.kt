package team.springpsring.petpartner.domain.feed.comment.dto

import java.time.LocalDateTime

class UpdateCommentRequest (
    val body: String,
    val createdAt: LocalDateTime,
)