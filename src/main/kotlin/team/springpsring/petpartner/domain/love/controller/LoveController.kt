package team.springpsring.petpartner.domain.love.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import team.springpsring.petpartner.domain.love.dto.LoveResponse
import team.springpsring.petpartner.domain.love.service.LoveService
import team.springpsring.petpartner.domain.user.dto.GetUserInfoRequest

@RestController
class LoveController(
    private val loveService: LoveService
) {
    @PostMapping("/feeds/{feedId}/loves/update")
    fun updateLoveForFeed(
        @PathVariable feedId: Long,
        @RequestBody getUserInfoRequest: GetUserInfoRequest
    ): ResponseEntity<Unit> {
        return loveService.checkValidate(getUserInfoRequest.token)
            .let {
                val isLove = loveService.isLove(feedId, it)
                ResponseEntity
                    .status(
                        if (isLove) HttpStatus.NO_CONTENT
                        else HttpStatus.CREATED
                    )
                    .body(loveService.updateLoveForFeed(feedId, isLove, it))
            }
    }

    @GetMapping("/feeds/{feedId}/loves/detail")
    fun getLoveUser(
        @PathVariable feedId: Long)
            : ResponseEntity<List<LoveResponse>> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(loveService.getLoveUser(feedId))
    }

    @PostMapping("/user/loves")
    fun getUsersLovedFeeds(@RequestBody getUserInfoRequest: GetUserInfoRequest)
    : ResponseEntity<List<LoveResponse>> {
        return loveService.checkValidate(getUserInfoRequest.token)
            .let {
                ResponseEntity
                    .status(HttpStatus.OK)
                    .body(loveService.getUsersLovedFeeds(it))
            }
    }
}