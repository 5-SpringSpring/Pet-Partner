package team.springpsring.petpartner.domain.user.service

import team.springpsring.petpartner.domain.user.repository.UserRepository
import jakarta.persistence.EntityNotFoundException
import jakarta.transaction.Transactional
import org.hibernate.service.spi.ServiceException
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.stereotype.Service
import team.springpsring.petpartner.domain.security.hash.BCHash
import team.springpsring.petpartner.domain.security.jwt.JwtUtil
import team.springpsring.petpartner.domain.user.dto.*
import team.springpsring.petpartner.domain.user.entity.User
import team.springpsring.petpartner.domain.user.entity.toResponse
import team.springpsring.petpartner.domain.user.loginUser.service.LoginUserService
import javax.naming.AuthenticationException

@Service
class UserService(
    private val loginService: LoginUserService,
    private val userRepository: UserRepository,
    private val jwtUtil: JwtUtil,
    private val encoder: BCHash,
){
    //!!!GlobalExceptionHandler 적용 후 Exception 종류 개선해야함

    @Transactional
    fun validateLoginIdFromToken(token:String):User {
        return jwtUtil.validateToken(token).let {
            userRepository.findByLoginId(it)
                ?: throw EntityNotFoundException("마 그런 아 없다")
        }
    }

    @Transactional
    fun signUpUser(request: SignUpUserRequest):Boolean{
        try {
            userRepository.save(request.toEntity(
                encoder.hashPassword(request.password)))
        } catch (e: DataIntegrityViolationException) {
            throw ServiceException("Data Duplication")
        }
        return true//임의로 넣음. 팀원과 조정할 것
    }

    @Transactional
    fun logInUser(request: LogInUserRequest):String {
        val token= userRepository.findByLoginId(request.loginId)
            ?.let { user ->
            if (encoder.verifyPassword(request.password, user.password)) {
                jwtUtil.generateAccessToken("loginId", user.loginId)
            }else throw AuthenticationException("User Password Not Match")
        } ?: throw EntityNotFoundException("User Not Found")
        loginService.login(request.loginId,token)
        return token
    }

    @Transactional
    fun getUserInfo(request:GetUserInfoRequest):UserResponse {
        return validateLoginIdFromToken(request.token)
            .toResponse()
    }

    @Transactional
    fun updatePassword(request:UpdateUserPasswordRequest):Boolean {
        validateLoginIdFromToken(request.token)
            .let {
                if(encoder.verifyPassword(request.oldPassword,it.password)){
                    if(!encoder.verifyPassword(request.newPassword,it.password)){
                        it.password=encoder.hashPassword(request.newPassword)
                    }
                    else throw AuthenticationException("바뀐게 없노")
                }
                else throw AuthenticationException("User Password Not Match")
            }
        return true //팀원이랑 협의 볼 것
    }

    @Transactional
    fun logoutUser(loginId:String):Boolean{
        loginService.logout(loginId)
        return true
    }
}