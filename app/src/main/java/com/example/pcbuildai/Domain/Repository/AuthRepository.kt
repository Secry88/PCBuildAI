package com.example.pcbuildai.Domain.Repository

import com.example.pcbuildai.Domain.Models.Profile
import com.example.pcbuildai.Domain.Models.User

interface AuthRepository {
    suspend fun signIn(email: String, password: String): User
    suspend fun signUp(email: String, password: String): User
    suspend fun getProfile(userId: String): Profile
}