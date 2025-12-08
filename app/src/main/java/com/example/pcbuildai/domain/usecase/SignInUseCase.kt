package com.example.pcbuildai.domain.usecase

import com.example.pcbuildai.domain.repository.AuthRepository

class SignInUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke (email: String, password: String) = authRepository.signIn(email, password)
}