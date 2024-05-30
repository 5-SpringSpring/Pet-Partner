package team.springpsring.petpartner.domain.feed.comment.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import team.springpsring.petpartner.domain.feed.comment.dto.CommentResponse
import team.springpsring.petpartner.domain.feed.comment.dto.CreateCommentRequest
import team.springpsring.petpartner.domain.feed.comment.dto.UpdateCommentRequest
import team.springpsring.petpartner.domain.feed.comment.entity.Comment
import team.springpsring.petpartner.domain.feed.comment.entity.toResponse
import team.springpsring.petpartner.domain.feed.comment.repository.CommentRepository
import team.springpsring.petpartner.domain.feed.repository.FeedRepository
import team.springpsring.petpartner.domain.user.dto.GetUserInfoRequest
import team.springpsring.petpartner.domain.user.dto.UserResponse
import team.springpsring.petpartner.domain.user.service.UserService

@Service
class CommentService(
    private val feedRepository: FeedRepository,
    private val commentRepository: CommentRepository,
    private val userService: UserService
){

    @Transactional
    fun createComment(feedId:Long, createCommentRequest: CreateCommentRequest, username:String): CommentResponse {
        val feed = feedRepository.findByIdOrNull(feedId) ?: throw NullPointerException("Feed not found")
        val comment = Comment(
            username,
            createCommentRequest.body,
            createCommentRequest.createdAt,
            feed
        )
        feed.addComment(comment)
        commentRepository.save(comment)

        return comment.toResponse()
    }

    @Transactional
    fun updateComment(feedId: Long, commentId: Long, updateCommentRequest: UpdateCommentRequest): CommentResponse{
        val comment = commentRepository.findByFeedIdAndId(feedId, commentId) ?:
        throw NullPointerException("Feed not found")

        comment.body = updateCommentRequest.body
        comment.createdAt =updateCommentRequest.createdAt

        return commentRepository.save(comment).toResponse()
    }

    @Transactional
    fun deleteComment(feedId: Long,commentId: Long){

        val feed = feedRepository.findByIdOrNull(feedId) ?: throw NullPointerException("Feed not found")
        val comment = commentRepository.findByFeedIdAndId(feedId, commentId) ?:
        throw NullPointerException("Feed not found")

        feed.deleteComment(comment)
        commentRepository.delete(comment)
    }

    fun validateToken(token:String):UserResponse{
        return userService.getUserInfo(GetUserInfoRequest(token))
    }

    fun checkOwner(token:String, commentId:Long, feedId:Long):Boolean{
        val comment = commentRepository.findByFeedIdAndId(feedId, commentId) ?:
        throw NullPointerException("Feed not found")

        return validateToken(token).username == comment.username
    }
}