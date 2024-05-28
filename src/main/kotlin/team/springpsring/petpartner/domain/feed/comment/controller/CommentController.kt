package team.springpsring.petpartner.domain.feed.comment.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import team.springpsring.petpartner.domain.feed.comment.dto.CommentResponse
import team.springpsring.petpartner.domain.feed.comment.dto.CreateCommentRequest
import team.springpsring.petpartner.domain.feed.comment.dto.UpdateCommentRequest
import team.springpsring.petpartner.domain.feed.comment.service.CommentService

@RequestMapping("/feeds/{feedId}/comments")
@RestController
class CommentController(
    private val commentService: CommentService,
) {

    @PostMapping
    fun createComment(
        @PathVariable feedId: Long,
        @RequestBody createCommentRequest: CreateCommentRequest,
    ): ResponseEntity<CommentResponse> {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(commentService.createComment(feedId, createCommentRequest))
    }

    @PutMapping("/{commentId}")
    fun updateComment(
        @PathVariable feedId: Long,
        @PathVariable commentId: Long,
        @RequestBody updateCommentRequest: UpdateCommentRequest, name: String, password: String,
    ): ResponseEntity<CommentResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(commentService.updateComment(feedId, commentId, updateCommentRequest, name, password))
    }

    @DeleteMapping("/{commentId}")
    fun deleteComment(
        @PathVariable feedId: Long,
        @PathVariable commentId: Long,
        name: String,
        password: String,
    ): ResponseEntity<Unit> {
        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .body(commentService.deleteComment(feedId, commentId, name, password))
    }
}