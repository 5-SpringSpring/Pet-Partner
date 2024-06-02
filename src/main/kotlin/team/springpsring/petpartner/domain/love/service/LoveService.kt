package team.springpsring.petpartner.domain.love.service

import jakarta.persistence.EntityNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import team.springpsring.petpartner.domain.feed.repository.FeedRepository
import team.springpsring.petpartner.domain.love.dto.LoveResponse
import team.springpsring.petpartner.domain.love.entity.Love
import team.springpsring.petpartner.domain.love.entity.toResponse
import team.springpsring.petpartner.domain.love.repository.LoveRepository
import team.springpsring.petpartner.domain.user.dto.GetUserInfoRequest
import team.springpsring.petpartner.domain.user.dto.UserResponse
import team.springpsring.petpartner.domain.user.service.UserService
import javax.naming.AuthenticationException

@Service
class LoveService(
    private val loveRepository: LoveRepository,
    private val feedRepository: FeedRepository,
    private val userService: UserService
) {

    @Transactional
    fun checkValidate(token: String):UserResponse{
        return userService.getUserInfo(
            GetUserInfoRequest(
                token=token)
        )
    }

    @Transactional
    fun updateLoveForFeed(feedId: Long, isLove: Boolean, userInfo: UserResponse) {
        if(isLove) deleteLove(feedId,userInfo)
        else createLove(feedId, userInfo.username)
    }

    private fun createLove(feedId:Long, username:String): LoveResponse {
        val feed = feedRepository.findByIdOrNull(feedId) ?: throw NullPointerException("Feed not found")
        if (feed.name == username){
            throw AuthenticationException("this user's feed: user cannot make mark on their feeds")
        }

        val love = Love(
            username,
            feed = feed
        )
        loveRepository.save(love)
        return love.toResponse()
    }

    private fun deleteLove(feedId:Long, userInfo:UserResponse) {
        val username = userInfo.username
        val love = loveRepository.findByFeedIdAndUsername(feedId, username)?: throw EntityNotFoundException("Love not found")
        loveRepository.delete(love)
    }

    fun countLove(feedId:Long):Int{
        return loveRepository.countLoveByFeedId(feedId)
    }

    fun isLove(feedId:Long, userInfo:UserResponse):Boolean{
        return loveRepository.existsByFeedIdAndUsername(feedId, userInfo.username)
    }

    fun getLoveUser(feedId: Long):List<LoveResponse>{
        return loveRepository.findByFeedId(feedId).map{it.toResponse()}
    }

    fun getUsersLovedFeeds(userInfo : UserResponse): List<LoveResponse>
    {
        val username = userInfo.username
        return loveRepository.findByUsername(username).map { it.toResponse() }
    }
}