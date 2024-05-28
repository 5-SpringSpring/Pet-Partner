package team.springpsring.petpartner.domain.feed.comment.dto

import java.time.LocalDateTime

class CreateCommentRequest (
    val name: String,
    val password: String,
    val body: String,
    val createdAt: LocalDateTime,
)