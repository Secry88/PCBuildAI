package com.example.pcbuildai.domain.usecase.auth

data class AuthUseCases(
    val signInUseCase: SignInUseCase,
    val signUpUseCase: SignUpUseCase,
    val getProfileUseCase: GetProfileUseCase
)
