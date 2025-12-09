package com.example.pcbuildai.data.repository

import com.example.pcbuildai.data.remote.services.AuthService
import com.example.pcbuildai.domain.models.User
import com.example.pcbuildai.domain.repository.AuthRepository

class AuthRepositoryImpl(private val authService: AuthService) : AuthRepository {
    override suspend fun signUp(email: String, password: String): User {
        return authService.signUp(email, password)
    }

    override suspend fun signIn(email: String, password: String): User {
        return  authService.signIn(email, password)
    }
}