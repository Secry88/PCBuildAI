package com.example.pcbuildai.domain.usecase.auth

import com.example.pcbuildai.domain.models.Profile
import com.example.pcbuildai.domain.repository.AuthRepository

class UpdateProfileUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(userId: String, profile: Profile): Profile {
        return repository.updateProfile(userId, profile)
    }
}