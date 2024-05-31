package team.springpsring.petpartner.domain.user.loginUser.service

import jakarta.persistence.EntityNotFoundException
import jakarta.transaction.Transactional
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.stereotype.Service
import team.springpsring.petpartner.domain.user.loginUser.entity.LoginUser
import team.springpsring.petpartner.domain.user.loginUser.repository.LoginUserRepository

@Service
@Transactional
class LoginUserService(
    private val loginUserRepository: LoginUserRepository
) {
    fun login(loginId: String, token: String): Boolean{
        if(loginUserRepository.existsByLoginId(loginId)){
            loginUserRepository.findByLoginId(loginId)?.token=token
        }
        else{
            loginUserRepository.save(
                LoginUser(
                    loginId = loginId,
                    token = token
                )
            )
        }
        return true
    }

    fun logout(loginId: String):Boolean {
        loginUserRepository.deleteByLoginId(loginId)
        return true
    }

    fun checkLoginStatus(loginId: String, token: String){
        if(!loginUserRepository.existsByLoginIdAndToken(loginId,token)) {
            throw DataIntegrityViolationException("Invalid Login User")
        }
    }
}