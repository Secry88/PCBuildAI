<<<<<<<< HEAD:app/src/main/java/com/example/pcbuildai/domain/usecase/SignInUseCase.kt
package com.example.pcbuildai.domain.usecase
========
package com.example.pcbuildai.domain.usecase.auth
>>>>>>>> feature/supabse-auth-impl:app/src/main/java/com/example/pcbuildai/domain/usecase/auth/SignInUseCase.kt

import com.example.pcbuildai.domain.repository.AuthRepository

class SignInUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke (email: String, password: String) = authRepository.signIn(email, password)
}