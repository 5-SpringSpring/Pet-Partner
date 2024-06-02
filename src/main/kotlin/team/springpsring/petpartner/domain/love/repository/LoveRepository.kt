package team.springpsring.petpartner.domain.love.repository

import org.springframework.data.jpa.repository.JpaRepository
import team.springpsring.petpartner.domain.love.entity.Love

interface LoveRepository : JpaRepository<Love, Long> {
    fun findByFeedIdAndUsername(feedId:Long, username:String):Love?
    fun countLoveByFeedId(feedId:Long):Int
    fun existsByFeedIdAndUsername(feedId:Long, username:String):Boolean
    fun findByFeedId(feedId:Long):List<Love>
    fun findByUsername(username:String):List<Love>
}