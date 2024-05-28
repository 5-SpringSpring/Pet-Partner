package team.springpsring.petpartner.domain.feed.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import team.springpsring.petpartner.domain.feed.dto.CreateFeedRequest
import team.springpsring.petpartner.domain.feed.dto.FeedResponse
import team.springpsring.petpartner.domain.feed.dto.UpdateFeedRequest
import team.springpsring.petpartner.domain.feed.entity.Feed
import team.springpsring.petpartner.domain.feed.entity.toResponse
import team.springpsring.petpartner.domain.feed.repository.FeedRepository

@Service
class FeedService(private val feedRepository: FeedRepository) {

    fun getAllFeeds(): List<FeedResponse> {
        return feedRepository.findAll().map { it.toResponse() }
    }

    fun getFeedById(feedId: Long): FeedResponse {
        val feed = feedRepository.findByIdOrNull(feedId) ?: throw NullPointerException("Feed not found")
        return feed.toResponse()
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
                0,0,
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


}