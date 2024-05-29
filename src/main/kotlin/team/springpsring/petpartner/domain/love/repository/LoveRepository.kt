package team.springpsring.petpartner.domain.love.repository

import org.springframework.data.jpa.repository.JpaRepository
import team.springpsring.petpartner.domain.love.entity.Love

interface LoveRepository : JpaRepository<Love, Long> {
    fun findAllByFeedId(feedId:Long):List<Love>
    fun findAllByLoginId(loginId:String):List<Love>
    fun findByFeedIdAndLoginId(feedId:Long, loginId:String):Love?
}