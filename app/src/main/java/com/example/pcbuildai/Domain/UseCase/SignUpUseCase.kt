package com.example.pcbuildai.Domain.UseCase

import com.example.pcbuildai.Domain.Models.User
import com.example.pcbuildai.Domain.Repository.AuthRepository

class SignUpUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(email: String, password: String) = repository.signUp(email, password)
}