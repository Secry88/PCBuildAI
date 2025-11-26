package com.example.pcbuildai.domain.usecase

import com.example.pcbuildai.domain.repository.AuthRepository

class GetProfileUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(userId: String) = repository.getProfile(userId)
}