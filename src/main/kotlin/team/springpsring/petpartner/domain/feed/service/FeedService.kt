package team.springpsring.petpartner.domain.feed.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import team.springpsring.petpartner.domain.feed.comment.entity.toResponse
import team.springpsring.petpartner.domain.feed.comment.repository.CommentRepository
import team.springpsring.petpartner.domain.feed.dto.CreateFeedRequest
import team.springpsring.petpartner.domain.feed.dto.DeleteFeedRequest
import team.springpsring.petpartner.domain.feed.dto.FeedResponse
import team.springpsring.petpartner.domain.feed.dto.UpdateFeedRequest
import team.springpsring.petpartner.domain.feed.entity.Feed
import team.springpsring.petpartner.domain.feed.entity.toResponse
import team.springpsring.petpartner.domain.feed.repository.FeedRepository
import team.springpsring.petpartner.domain.love.dto.CreateLoveRequest
import team.springpsring.petpartner.domain.love.repository.LoveRepository
import team.springpsring.petpartner.domain.love.service.LoveService
import team.springpsring.petpartner.domain.user.dto.GetUserInfoRequest
import team.springpsring.petpartner.domain.user.dto.UserResponse
import team.springpsring.petpartner.domain.user.entity.User
import team.springpsring.petpartner.domain.user.service.UserService

@Service
class FeedService(
    private val feedRepository: FeedRepository,
    private val commentRepository: CommentRepository,
    private val loveRepository: LoveRepository,
    private val loveService: LoveService,
    private val userService: UserService
) {

    @Transactional
    fun checkValidate(token: String):UserResponse{
        return userService.getUserInfo(
            GetUserInfoRequest(
                token=token))
    }

    fun getAllFeeds(): List<FeedResponse> {
        return feedRepository.findAll().map { it.toResponse() }
    }

    fun getFeedById(feedId: Long): FeedResponse {
        val feed = feedRepository.findByIdOrNull(feedId) ?: throw NullPointerException("Feed not found")
        val comments = commentRepository.findById(feedId).map { listOf(it.toResponse()) }.orElseGet { emptyList() }

        return FeedResponse(
            feedId,
            feed.name,
            feed.title,
            feed.body,
            feed.images,
            feed.category,
            feed.views++,
            feed.created,
            comments
        )
    }

    @Transactional
    fun createFeed(userInfo:UserResponse,createFeedRequest: CreateFeedRequest): FeedResponse {
        return feedRepository.save(
            Feed(
                userInfo.username,
                createFeedRequest.title,
                createFeedRequest.body,
                createFeedRequest.images,
                createFeedRequest.category,
            )
        ).toResponse()
    }

    @Transactional
    fun updateFeed(userInfo:UserResponse ,feedId: Long, updateFeedRequest: UpdateFeedRequest): FeedResponse {
        val feed = feedRepository.findByIdOrNull(feedId) ?: throw NullPointerException("Feed not found")
        if(feed.name!=userInfo.username) throw ArithmeticException("userNotMatch")
        val (title, body, images, category) = updateFeedRequest

        feed.title = title
        feed.body = body
        feed.images = images
        feed.category = category

        return feedRepository.save(feed).toResponse()
    }

    @Transactional
    fun deleteFeed(userInfo:UserResponse ,feedId: Long) {
        val feed = feedRepository.findByIdOrNull(feedId) ?: throw NullPointerException("Feed not found")
        if(feed.name!=userInfo.username) throw ArithmeticException("userNotMatch")
        feedRepository.delete(feed)
    }

    @Transactional
    fun updateLoveForFeed(feedId: Long, isLove: Boolean, userInfo: UserResponse) {
        if(isLove){
            val love = loveRepository.findByFeedIdAndLoginId(feedId,userInfo.loginId)?:throw NullPointerException("Love not found")
            loveService.deleteLove(love.id!!)
        }
        else{
            loveService.createLove(feedId, userInfo.loginId)
        }
    }
}