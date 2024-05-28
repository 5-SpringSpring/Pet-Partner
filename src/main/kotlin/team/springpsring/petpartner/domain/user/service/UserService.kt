package com.teamsparta.todoapp.domain.user.service

import com.teamsparta.todoapp.domain.user.dto.LogInUserRequest
import com.teamsparta.todoapp.domain.user.dto.SignUpUserRequest
import com.teamsparta.todoapp.domain.user.model.User
import com.teamsparta.todoapp.domain.user.repository.UserRepository
import jakarta.persistence.EntityNotFoundException
import jakarta.transaction.Transactional
import org.hibernate.service.spi.ServiceException
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.stereotype.Service
import team.springpsring.petpartner.domain.security.hash.BCHash
import team.springpsring.petpartner.domain.security.jwt.JwtUtil
import javax.naming.AuthenticationException

@Service
class UserService(
    private val userRepository: UserRepository,
    private val jwtUtil: JwtUtil,
    private val passwordEncoder: BCHash,
){

    @Transactional
    fun signUpUser(request: SignUpUserRequest) {
        try {
            userRepository.save(
                User(
                    userId = request.userId,
                    userPassword = BCHash.hashPassword(request.userPassword)
                )
            )
        } catch (e: DataIntegrityViolationException) {
            throw ServiceException("Data Duplication")
        }
    }

    @Transactional
    fun logInUser(request: LogInUserRequest):String {
        val dbPassword = userRepository.findByUserId(request.userId)
            ?: throw EntityNotFoundException("User Not Found")
        if (BCHash.verifyPassword(request.userPassword, dbPassword.userPassword))
        {
            return JwtUtil.generateAccessToken("userId", request.userId)
        }
        else throw AuthenticationException("User Password Not Match")
    }

    @Transactional
    fun getUserInfo(token:String): String{
        return JwtUtil.getUserIdFromToken(token)!!
    }
}