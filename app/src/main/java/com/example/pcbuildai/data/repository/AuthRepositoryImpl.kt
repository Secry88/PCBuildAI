package com.example.pcbuildai.data.repository

import com.example.pcbuildai.data.remote.services.AuthService
import com.example.pcbuildai.domain.models.User
import com.example.pcbuildai.domain.repository.AuthRepository

class AuthRepositoryImpl(private val authService: AuthService) : AuthRepository {
    override suspend fun signUp(email: String, password: String): User {
        val response = authService.signUp(email, password)
        val dto = response.
    }

}