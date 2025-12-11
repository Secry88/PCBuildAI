package com.example.pcbuildai.domain.usecase.profile

import com.example.pcbuildai.domain.models.Profile
import com.example.pcbuildai.domain.repository.ProfileRepository

class UpdateProfileUseCase(private val repository: ProfileRepository) {
    suspend operator fun invoke(userId: String, profile: Profile): Profile {
        return repository.updateProfile(userId, profile)
    }
}