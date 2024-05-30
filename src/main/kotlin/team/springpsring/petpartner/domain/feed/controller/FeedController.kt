package team.springpsring.petpartner.domain.feed.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import team.springpsring.petpartner.domain.feed.dto.*
import team.springpsring.petpartner.domain.feed.entity.CategoryType
import team.springpsring.petpartner.domain.feed.service.FeedService
import team.springpsring.petpartner.domain.love.dto.CreateLoveRequest


@RestController
@RequestMapping("/feeds")
class FeedController(
    private val feedService: FeedService,
) {

    @GetMapping
    fun getAllFeeds(): ResponseEntity<List<FeedResponse>> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(feedService.getAllFeeds())
    }

    @GetMapping("/{feedId}")
    fun getFeedById(@PathVariable feedId: Long): ResponseEntity<FeedResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(feedService.getFeedById(feedId))
    }

    @GetMapping("/categories")
    fun getCategoryFeeds(@RequestParam category: CategoryType)
    : ResponseEntity<List<FeedResponse>> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(feedService.getFeedByCategory(category))
    }

    @PostMapping("/username")
    fun getUsernameFeeds(@RequestBody getUsernameFeedRequest: GetUsernameFeedRequest)
            : ResponseEntity<List<FeedResponse>> {
        return feedService.checkValidate(getUsernameFeedRequest.token)
            .let {
                ResponseEntity
                    .status(HttpStatus.OK)
                    .body(feedService.getFeedByUsername(it))
            }
    }

    @PostMapping
    fun createFeed(@RequestBody createFeedRequest: CreateFeedRequest): ResponseEntity<FeedResponse> {
        return feedService.checkValidate(createFeedRequest.token)
            .let {
                ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(feedService.createFeed(it, createFeedRequest))
            }
    }

    @PutMapping("/{feedId}")
    fun updateFeed(
        @PathVariable feedId: Long,
        @RequestBody updateFeedRequest: UpdateFeedRequest
    ): ResponseEntity<FeedResponse> {
        return feedService.checkValidate(updateFeedRequest.token)
            .let {
                ResponseEntity
                    .status(HttpStatus.OK)
                    .body(feedService.updateFeed(it, feedId, updateFeedRequest))
            }
    }

    @DeleteMapping("/{feedId}")
    fun deleteFeed(@PathVariable feedId: Long, @RequestBody deleteFeedRequest: DeleteFeedRequest): ResponseEntity<Unit> {
        return feedService.checkValidate(deleteFeedRequest.token)
            .let {
                ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .body(feedService.deleteFeed(it, feedId))
            }
    }

    @PostMapping("/{feedId}/loves")
    fun updateLoveForFeed(@PathVariable feedId:Long, @RequestParam isLove:Boolean, @RequestBody createLoveRequest: CreateLoveRequest): ResponseEntity<Unit> {
        return feedService.checkValidate(createLoveRequest.token)
            .let {
                ResponseEntity
                    .status(
                        if (isLove) HttpStatus.NO_CONTENT
                        else HttpStatus.CREATED)
                    .body(feedService.updateLoveForFeed(feedId, isLove, it))
            }
    }
}