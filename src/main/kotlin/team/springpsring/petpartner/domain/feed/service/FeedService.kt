package team.springpsring.petpartner.domain.feed.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import team.springpsring.petpartner.domain.feed.comment.entity.toResponse
import team.springpsring.petpartner.domain.feed.comment.repository.CommentRepository
import team.springpsring.petpartner.domain.feed.comment.service.CommentService
import team.springpsring.petpartner.domain.feed.dto.CreateFeedRequest
import team.springpsring.petpartner.domain.feed.dto.FeedResponse
import team.springpsring.petpartner.domain.feed.dto.UpdateFeedRequest
import team.springpsring.petpartner.domain.feed.entity.Feed
import team.springpsring.petpartner.domain.feed.entity.toResponse
import team.springpsring.petpartner.domain.feed.repository.FeedRepository
import team.springpsring.petpartner.domain.love.dto.CreateLoveRequest
import team.springpsring.petpartner.domain.love.entity.toResponse
import team.springpsring.petpartner.domain.love.repository.LoveRepository
import team.springpsring.petpartner.domain.love.service.LoveService

@Service
class FeedService(
    private val feedRepository: FeedRepository,
    private val commentRepository: CommentRepository,
    private val loveRepository: LoveRepository,
    private val commentService: CommentService,
    private val loveService: LoveService
) {

    fun getAllFeeds(): List<FeedResponse> {
        return feedRepository.findAll().map { it.toResponse() }
    }

    fun getFeedById(feedId: Long): FeedResponse {
        val feed = feedRepository.findByIdOrNull(feedId) ?: throw NullPointerException("Feed not found")

        val comments = commentRepository.findById(feedId).map { listOf(it.toResponse()) }.orElseGet { emptyList() }
        val loves = loveRepository.findById(feedId).map { listOf(it.toResponse()) }.orElseGet { emptyList() }

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
    fun createFeed(createFeedRequest: CreateFeedRequest): FeedResponse {
        return feedRepository.save(
            Feed(
                createFeedRequest.name,
                createFeedRequest.title,
                createFeedRequest.body,
                createFeedRequest.images,
                createFeedRequest.category,
                0,
                createFeedRequest.created
            )
        ).toResponse()
    }

    @Transactional
    fun updateFeed(feedId: Long, updateFeedRequest: UpdateFeedRequest): FeedResponse {
        val feed = feedRepository.findByIdOrNull(feedId) ?: throw NullPointerException("Feed not found")
        val (title, body, images, category) = updateFeedRequest

        feed.title = title
        feed.body = body
        feed.images = images
        feed.category = category

        return feedRepository.save(feed).toResponse()
    }

    @Transactional
    fun deleteFeed(feedId: Long) {
        val feed = feedRepository.findByIdOrNull(feedId) ?: throw NullPointerException("Feed not found")
        feedRepository.delete(feed)
    }

    @Transactional
    fun updateLoveForFeed(feedId: Long, isLove: Boolean, createLoveRequest: CreateLoveRequest) {
        if(isLove){
            val love = loveRepository.findByFeedIdAndLoginId(feedId,createLoveRequest.loginId)?:throw NullPointerException("Love not found")
            loveService.deleteLove(love.id!!)
        }
        else{
            loveService.createLove(feedId, createLoveRequest)
        }
    }
}