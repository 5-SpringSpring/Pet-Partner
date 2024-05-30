package team.springpsring.petpartner.domain.feed.comment.entity

import jakarta.persistence.*
import team.springpsring.petpartner.domain.feed.comment.dto.CommentResponse
import team.springpsring.petpartner.domain.feed.entity.Feed
import java.time.LocalDateTime

@Entity
@Table(name="comment")
class Comment (

    @Column(name = "name", nullable = false)
    var name:String,

   // @Column(name = "password", nullable = false)
   // var password:String,

    @Column(name = "body", nullable = false)
    var body:String,

//    @Column(name = "loves", nullable = false)
//    var loves :Int,

    @Column(name = "created_at", nullable = false)
    var createdAt: LocalDateTime,

    @ManyToOne
    @JoinColumn(name = "feed_id", nullable = false)
    var feed: Feed,
){
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id:Long?=null
}

fun Comment.toResponse()
        : CommentResponse {
    return CommentResponse(
        id = id!!,
        name = name,
        body = body,
      //  loves = loves,
        createdAt = createdAt,
    )
}