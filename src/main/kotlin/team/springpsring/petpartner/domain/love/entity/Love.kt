package team.springpsring.petpartner.domain.love.entity

import jakarta.persistence.*
import team.springpsring.petpartner.domain.love.dto.LoveResponse


@Entity
@Table(name="love")
class Love (

    @Column(name="login_id")
    var loginId : String,

    @Column(name="feed_id")
    var feedId : Long,
){
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id : Long? = null
}

fun Love.toResponse():LoveResponse{
    return LoveResponse(this.loginId, this.feedId)
}