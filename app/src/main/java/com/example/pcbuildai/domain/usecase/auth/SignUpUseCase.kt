<<<<<<<< HEAD:app/src/main/java/com/example/pcbuildai/domain/usecase/SignUpUseCase.kt
package com.example.pcbuildai.domain.usecase
========
package com.example.pcbuildai.domain.usecase.auth
>>>>>>>> feature/supabse-auth-impl:app/src/main/java/com/example/pcbuildai/domain/usecase/auth/SignUpUseCase.kt

import com.example.pcbuildai.domain.repository.AuthRepository

class SignUpUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(email: String, password: String) = repository.signUp(email, password)
}