package com.example.pcbuildai.domain.mapper

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
    object AuthErrorMapper {
        fun map(errorBody: String): String {
            return try {
                val json = Json.parseToJsonElement(errorBody).jsonObject
                val errorCode = json["error_code"]?.jsonPrimitive?.contentOrNull

                when(errorCode) {
                    "weak_password" -> "Пароль должен содержать минимум 6 символов"
                    "user_already_exists" -> "Пользователь с таким email уже зарегистрирован"
                    "validation_failed" -> "Некорректный формат email"
                    "invalid_credentials" -> "Неверный логин или пароль"
                    else -> "Произошла неизвестная ошибка"
                }
            }catch (e: Exception) {
                errorBody
            }
        }
    }