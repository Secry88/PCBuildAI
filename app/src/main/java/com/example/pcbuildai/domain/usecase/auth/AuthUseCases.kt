package com.example.pcbuildai.domain.usecase.auth

import com.example.pcbuildai.domain.usecase.SignInUseCase
import com.example.pcbuildai.domain.usecase.SignUpUseCase

data class AuthUseCases(
    val signInUseCase: SignInUseCase,
    val signUpUseCase: SignUpUseCase,
    val getProfileUseCase: GetProfileUseCase
)
