package com.example.pcbuildai.domain.validation

import android.util.Patterns

object EmailValidator {
    fun validate(email: String): String? {
        return if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            "Введите корректный email"
        } else null
    }
}