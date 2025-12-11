package com.example.pcbuildai.domain.usecase.auth

import com.example.pcbuildai.domain.usecase.profile.GetProfileUseCase

data class AuthUseCases(
    val signInUseCase: SignInUseCase,
    val signUpUseCase: SignUpUseCase,
    val getProfileUseCase: GetProfileUseCase
)
