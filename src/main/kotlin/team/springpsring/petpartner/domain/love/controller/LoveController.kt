package team.springpsring.petpartner.domain.love.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import team.springpsring.petpartner.domain.love.dto.CreateLoveRequest
import team.springpsring.petpartner.domain.love.dto.LoveResponse
import team.springpsring.petpartner.domain.love.service.LoveService


@RestController
@RequestMapping("/loves")
class LoveController (private val loveService: LoveService) {

    //좋아요 생성
//    @PostMapping
//    fun createLove(feedId:Long, @RequestBody createLoveRequest: CreateLoveRequest): ResponseEntity<LoveResponse> {
//        return ResponseEntity.status(HttpStatus.CREATED).body(loveService.createLove(feedId,createLoveRequest))
//    }
//
//    //좋아요 삭제
//    @DeleteMapping("/{loveId}")
//    fun deleteLove(@PathVariable loveId: Long): ResponseEntity<Unit> {
//        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(loveService.deleteLove(loveId))
//    }

    //필터링의 경우는 각각의 Controller에서 구현하는게 좋을 것 같다.(아이디어 더 생각해봐야겠음)
    //좋아요 필터링 1 - 피드 기준
    //좋아요 필터링 2 - 사용자 기준

}