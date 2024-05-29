package team.springpsring.petpartner.domain.feed.entity

import jakarta.persistence.*
import team.springpsring.petpartner.domain.feed.comment.entity.Comment
import team.springpsring.petpartner.domain.feed.dto.FeedResponse
import java.time.LocalDateTime

@Entity
@Table(name = "feed")
class Feed(

    @Column(name = "name", nullable = false)
    var name: String,

    @Column(name = "title", nullable = false)
    var title: String,

    @Column(name = "body", nullable = false)
    var body: String,

    @Column(name = "images", nullable = false)
    var images: String,

    @Column(name = "category", nullable = false)
    var category: Int,

    @Column(name = "loves", nullable = false)
    var loves: Int=0,

    @Column(name = "views", nullable = false)
    var views: Int=0,

    @Column(name = "created_at", nullable = false)
    var created: LocalDateTime,

    @OneToMany(mappedBy = "feed", cascade = [(CascadeType.ALL)], orphanRemoval = true)
    var comments: MutableList<Comment> = mutableListOf(),
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    fun addComment(comment:Comment){
        comments.add(comment)
    }

    fun deleteComment(comment: Comment){
        comments.remove(comment)
    }

}

fun Feed.toResponse(): FeedResponse {
    return FeedResponse(
        id = id!!,
        name = name,
        title = title,
        body = body,
        images = images,
        category = category,
        loves = loves,
        views = views,
        created = created,
    )
}