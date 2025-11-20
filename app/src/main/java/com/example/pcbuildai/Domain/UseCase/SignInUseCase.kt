package com.example.pcbuildai.Domain.UseCase

import com.example.pcbuildai.Domain.Models.User
import com.example.pcbuildai.Domain.Repository.AuthRepository

class SignInUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke (email: String, password: String) = authRepository.signIn(email, password)
}