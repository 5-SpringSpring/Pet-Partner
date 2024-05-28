package team.springpsring.petpartner.domain.feed.entity

import jakarta.persistence.*
import team.springpsring.petpartner.domain.feed.comment.entity.Comment
import java.time.LocalDateTime

@Entity
@Table(name = "Feed")
class Feed(

    @Column(name = "name", nullable = false)
    var name: String,

    @Column(name = "title", nullable = false)
    var title:String,

    @Column(name = "body", nullable = false)
    var body:String,

    @Column(name = "imglist", nullable = false)
    var imgList:String,

    @Column(name = "category", nullable = false)
    var category:Int,

    @Column(name = "like", nullable = false)
    var like:Int,

    @Column(name = "view", nullable = false)
    var view:Int,

    @Column(name = "created_at", nullable = false)
    var created: LocalDateTime,

    @OneToMany(mappedBy="Feed",cascade = [(CascadeType.ALL)], orphanRemoval = true)
    var comments:MutableList<Comment> = mutableListOf(),
    ) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id:Long?=null
}