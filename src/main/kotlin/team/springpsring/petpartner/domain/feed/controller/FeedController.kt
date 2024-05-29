package team.springpsring.petpartner.domain.feed.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import team.springpsring.petpartner.domain.feed.dto.CreateFeedRequest
import team.springpsring.petpartner.domain.feed.dto.FeedResponse
import team.springpsring.petpartner.domain.feed.dto.UpdateFeedRequest
import team.springpsring.petpartner.domain.feed.service.FeedService
import team.springpsring.petpartner.domain.love.dto.CreateLoveRequest
import team.springpsring.petpartner.domain.love.dto.LoveResponse
import team.springpsring.petpartner.domain.love.service.LoveService


@RestController
@RequestMapping("/feeds")
class FeedController(
    private val feedService: FeedService,
    private val loveService: LoveService
) {

    @GetMapping
    fun getAllFeeds(): ResponseEntity<List<FeedResponse>> {
        return ResponseEntity.status(HttpStatus.OK).body(feedService.getAllFeeds())
    }

    @GetMapping("/{feedId}")
    fun getFeedById(@PathVariable feedId: Long): ResponseEntity<FeedResponse> {
        return ResponseEntity.status(HttpStatus.OK).body(feedService.getFeedById(feedId))
    }

    @PostMapping
    fun createFeed(@RequestBody createFeedRequest: CreateFeedRequest): ResponseEntity<FeedResponse> {
        return ResponseEntity.status(HttpStatus.CREATED).body(feedService.createFeed(createFeedRequest))
    }

    @PutMapping("/{feedId}")
    fun updateFeed(
        @PathVariable feedId: Long,
        @RequestBody updateFeedRequest: UpdateFeedRequest
    ): ResponseEntity<FeedResponse> {
        return ResponseEntity.status(HttpStatus.OK).body(feedService.updateFeed(feedId, updateFeedRequest))
    }

    @DeleteMapping("/{feedId}")
    fun deleteFeed(@PathVariable feedId: Long): ResponseEntity<Unit> {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(feedService.deleteFeed(feedId))
    }

    @PostMapping("/{feedId}/loves")
    fun updateLoveForFeed(@PathVariable feedId:Long, @RequestParam isLove:Boolean, @RequestBody createLoveRequest: CreateLoveRequest): ResponseEntity<Unit> {
        return ResponseEntity.status(if(isLove) HttpStatus.NO_CONTENT else HttpStatus.CREATED).body(feedService.updateLoveForFeed(feedId,isLove,createLoveRequest))
    }


}