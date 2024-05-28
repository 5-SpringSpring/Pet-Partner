package team.springpsring.petpartner.domain.feed.repository

import org.springframework.data.jpa.repository.JpaRepository
import team.springpsring.petpartner.domain.feed.entity.Feed

interface FeedRepository : JpaRepository<Feed, Long> {
}