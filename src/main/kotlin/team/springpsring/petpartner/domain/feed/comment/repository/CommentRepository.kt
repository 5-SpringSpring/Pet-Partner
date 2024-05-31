package team.springpsring.petpartner.domain.feed.comment.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import team.springpsring.petpartner.domain.feed.comment.entity.Comment

interface CommentRepository : JpaRepository<Comment, Long> {
    fun findByFeedIdAndId(feedId: Long, commentId: Long): Comment?
}