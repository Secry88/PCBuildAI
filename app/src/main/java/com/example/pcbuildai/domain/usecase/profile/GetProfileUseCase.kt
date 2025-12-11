package com.example.pcbuildai.domain.usecase.profile

import com.example.pcbuildai.domain.repository.ProfileRepository

class GetProfileUseCase(private val repository: ProfileRepository) {
    suspend operator fun invoke(userId: String) = repository.getProfile(userId)
}