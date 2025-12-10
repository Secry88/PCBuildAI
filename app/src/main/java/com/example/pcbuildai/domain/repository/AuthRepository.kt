package com.example.pcbuildai.domain.repository

import com.example.pcbuildai.domain.models.Profile
import com.example.pcbuildai.domain.models.User

interface AuthRepository {
    suspend fun signIn(email: String, password: String): User
    suspend fun signUp(email: String, password: String): User
    suspend fun getProfile(userId: String): Profile

    suspend fun updateProfile(userId: String, profile: Profile): Profile

}