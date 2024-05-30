package team.springpsring.petpartner.domain.feed.comment.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import team.springpsring.petpartner.domain.feed.comment.dto.CommentResponse
import team.springpsring.petpartner.domain.feed.comment.dto.CreateCommentRequest
import team.springpsring.petpartner.domain.feed.comment.dto.UpdateCommentRequest
import team.springpsring.petpartner.domain.feed.comment.service.CommentService
import team.springpsring.petpartner.domain.user.dto.GetUserInfoRequest

@RequestMapping("/feeds/{feedId}/comments")
@RestController
class CommentController(
    private val commentService: CommentService,
) {

    @PostMapping
    fun createComment(
        @PathVariable feedId: Long,
        @RequestBody createCommentRequest: CreateCommentRequest,
        getUserInfoRequest: GetUserInfoRequest
    ): ResponseEntity<CommentResponse> {

        return commentService.validateToken(getUserInfoRequest.token).let{
            ResponseEntity
                .status(HttpStatus.CREATED)
                .body(commentService.createComment(feedId, createCommentRequest,it.username))
        }
    }


    //로직을 변경해야함... 굳이 username을 받아올 필요가 있는지 진지하게 생각해보도록하라.
    @PutMapping("/{commentId}")
    fun updateComment(
        @PathVariable feedId: Long,
        @PathVariable commentId: Long,
        @RequestBody updateCommentRequest: UpdateCommentRequest,
        getUserInfoRequest: GetUserInfoRequest,
        username:String
    ): ResponseEntity<CommentResponse> {
        return commentService.checkOwner(getUserInfoRequest.token,feedId,commentId).let{
            ResponseEntity
                .status(HttpStatus.OK)
                .body(commentService.updateComment(feedId, commentId, updateCommentRequest))
        }
    }

    @DeleteMapping("/{commentId}")
    fun deleteComment(
        @PathVariable feedId: Long,
        @PathVariable commentId: Long,
        getUserInfoRequest: GetUserInfoRequest,
    ): ResponseEntity<Unit> {

        return commentService.checkOwner(getUserInfoRequest.token,feedId,commentId).let {
            ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(commentService.deleteComment(feedId, commentId))
        }
    }
}