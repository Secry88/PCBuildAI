package com.example.pcbuildai.domain.validation

object PasswordValidator {
    fun validate(password: String): String? {
        return if (password.length < 6) {
            "Пароль должен содержать минимум 6 символов"
        } else null
    }
}