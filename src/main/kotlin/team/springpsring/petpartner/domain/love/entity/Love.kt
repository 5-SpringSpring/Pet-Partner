package team.springpsring.petpartner.domain.love.entity

import jakarta.persistence.*
import team.springpsring.petpartner.domain.feed.entity.Feed
import team.springpsring.petpartner.domain.love.dto.LoveResponse


@Entity
@Table(name = "love")
class Love(

    @Column(name = "login_id",nullable = false)
    var loginId: String,

    //jpa에서 제공하는 연관관계를 활용하겠다.
    @ManyToOne
    @JoinColumn(name = "feed_id",nullable = false)
    var feed: Feed,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}

fun Love.toResponse(): LoveResponse {
    return LoveResponse(
        id = id!!,
        loginId = loginId
    )
}