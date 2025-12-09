package com.example.pcbuildai.domain.usecase.auth

import com.example.pcbuildai.domain.repository.AuthRepository

class SignUpUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(email: String, password: String) = repository.signUp(email, password)
}