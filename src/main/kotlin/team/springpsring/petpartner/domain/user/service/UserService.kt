package team.springpsring.petpartner.domain.user.service

import team.springpsring.petpartner.domain.user.dto.LogInUserRequest
import team.springpsring.petpartner.domain.user.dto.SignUpUserRequest
import team.springpsring.petpartner.domain.user.dto.toEntity
import team.springpsring.petpartner.domain.user.repository.UserRepository
import jakarta.persistence.EntityNotFoundException
import jakarta.transaction.Transactional
import org.hibernate.service.spi.ServiceException
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.stereotype.Service
import team.springpsring.petpartner.domain.security.hash.BCHash
import team.springpsring.petpartner.domain.security.jwt.JwtUtil
import team.springpsring.petpartner.domain.user.entity.User
import javax.naming.AuthenticationException

@Service
class UserService(
    private val userRepository: UserRepository,
    private val jwtUtil: JwtUtil,
    private val encoder: BCHash,
){
    //!!!GlobalExceptionHandler 적용 후 Exception 종류 개선해야함

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
        return userRepository.findByLoginId(request.loginId)
            ?.let { user ->
            if (encoder.verifyPassword(request.password, user.password)) {
                jwtUtil.generateAccessToken("loginId", user.loginId)
            }else throw AuthenticationException("User Password Not Match")
        } ?: throw EntityNotFoundException("User Not Found")
    }

    @Transactional
    fun validateLoginIdFromToken(token:String):User {
        return jwtUtil.validateToken(token).let {
            userRepository.findByLoginId(it)
                ?: throw EntityNotFoundException("마 그런 아 없다")
        }
    }
}