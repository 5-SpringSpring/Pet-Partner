package team.springpsring.petpartner.domain.feed.comment.entity

import jakarta.persistence.*
import team.springpsring.petpartner.domain.feed.entity.Feed
import java.time.LocalDateTime

@Entity
@Table(name="comment")
class Comment (

    @Column(name = "name", nullable = false)
    var name:String,

    @Column(name = "password", nullable = false)
    var password:String,

    @Column(name = "body", nullable = false)
    var body:String,

    @Column(name = "like", nullable = false)
    var like:String,

    @Column(name = "created_at", nullable = false)
    var created: LocalDateTime,

    @ManyToOne
    @JoinColumn(name = "feed_id", nullable = false)
    var feed: Feed,
){
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id:Long?=null
}