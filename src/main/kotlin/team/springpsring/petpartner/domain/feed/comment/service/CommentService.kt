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

@Service
class CommentService (private val feedRepository: FeedRepository, private val commentRepository: CommentRepository){

    @Transactional
    fun createComment(feedId:Long, createCommentRequest: CreateCommentRequest): CommentResponse {
        val feed = feedRepository.findByIdOrNull(feedId) ?: throw NullPointerException("Feed not found")
        val comment = Comment(
            createCommentRequest.name,
           // createCommentRequest.password,
            createCommentRequest.body,
            //loves = 0,
            createCommentRequest.createdAt,
            feed
        )
        feed.addComment(comment)
        commentRepository.save(comment)

        return comment.toResponse()
    }

    @Transactional
    fun updateComment(feedId: Long, commentId: Long, updateCommentRequest: UpdateCommentRequest,
                      name:String, password:String): CommentResponse{

        val comment = commentRepository.findByFeedIdAndId(feedId, commentId) ?:
        throw NullPointerException("Feed not found")

       // if(name!=comment.name || password !=comment.password){
         //   throw IllegalArgumentException("Can't update comment")
       // }

        comment.body = updateCommentRequest.body
        comment.createdAt =updateCommentRequest.createdAt

        return commentRepository.save(comment).toResponse()
    }

    @Transactional
    fun deleteComment(feedId: Long,commentId: Long, name:String, password:String){

        val feed = feedRepository.findByIdOrNull(feedId) ?: throw NullPointerException("Feed not found")
        val comment = commentRepository.findByFeedIdAndId(feedId, commentId) ?:
        throw NullPointerException("Feed not found")

        //if(name!=comment.name || password !=comment.password){
         //   throw IllegalArgumentException("Can't update comment")
       // }
        feed.deleteComment(comment)
        commentRepository.delete(comment)
    }
}