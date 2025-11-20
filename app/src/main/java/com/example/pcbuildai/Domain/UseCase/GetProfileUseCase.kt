package com.example.pcbuildai.Domain.UseCase

import com.example.pcbuildai.Domain.Models.Profile
import com.example.pcbuildai.Domain.Repository.AuthRepository

class GetProfileUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(userId: String) = repository.getProfile(userId)
}