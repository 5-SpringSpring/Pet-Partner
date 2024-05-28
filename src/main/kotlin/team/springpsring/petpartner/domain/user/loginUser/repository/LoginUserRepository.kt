package team.springpsring.petpartner.domain.user.loginUser.repository

import org.springframework.data.jpa.repository.JpaRepository
import team.springpsring.petpartner.domain.user.loginUser.entity.LoginUser

interface LoginUserRepository: JpaRepository<LoginUser, Long>{
    fun findByLoginId(loginId: String): LoginUser?
    fun deleteByLoginId(loginId: String)
}