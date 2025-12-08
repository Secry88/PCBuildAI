package com.example.pcbuildai.data.remote.services

import com.example.pcbuildai.data.remote.SupabaseConfig
import com.example.pcbuildai.domain.models.User
import com.example.pcbuildai.data.remote.dto.AuthResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import toDomain

class AuthService(private val client: HttpClient) {

    suspend fun signUp(email: String, password: String): User {
        val response = client.post("${SupabaseConfig.BASE_URL}/auth/v1/signup") {
            header("apikey", SupabaseConfig.ANON_KEY)
            contentType(ContentType.Application.Json)
            setBody(mapOf("email" to email, "password" to password))
        }

        val body = response.bodyAsText()
        println("SIGNUP RESPONSE: $body")  // логируем JSON
        val authResponse = try {
            kotlinx.serialization.json.Json { ignoreUnknownKeys = true; isLenient = true }
                .decodeFromString<AuthResponse>(body)
        } catch (e: Exception) {
            throw Exception("Ошибка при разборе ответа: ${e.message}\nBody: $body")
        }

        return authResponse.toDomain()
    }

    suspend fun signIn(email: String, password: String): User {
        val response = client.post("${SupabaseConfig.BASE_URL}/auth/v1/token?grant_type=password") {
            header("apikey", SupabaseConfig.ANON_KEY)
            contentType(ContentType.Application.Json)
            setBody(mapOf("email" to email, "password" to password))
        }

        val body = response.bodyAsText()
        println("SIGNIN RESPONSE: $body")  // логируем JSON
        val authResponse = try {
            kotlinx.serialization.json.Json { ignoreUnknownKeys = true; isLenient = true }
                .decodeFromString<AuthResponse>(body)
        } catch (e: Exception) {
            throw Exception("Ошибка при разборе ответа: ${e.message}\nBody: $body")
        }

        return authResponse.toDomain()
    }
}
