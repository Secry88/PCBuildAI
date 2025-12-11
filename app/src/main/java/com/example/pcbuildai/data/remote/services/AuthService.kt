package com.example.pcbuildai.data.remote.services

import com.example.pcbuildai.data.remote.SupabaseConfig
import com.example.pcbuildai.domain.models.User
import com.example.pcbuildai.data.remote.dto.AuthResponse
import com.example.pcbuildai.domain.mapper.AuthErrorMapper
import io.ktor.client.HttpClient
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import toDomain

@Suppress("JSON_FORMAT_REDUNDANT")
class AuthService(private val client: HttpClient) {

    suspend fun signUp(email: String, password: String): User {
        val response = client.post("${SupabaseConfig.BASE_URL}/auth/v1/signup") {
            header("apikey", SupabaseConfig.ANON_KEY)
            contentType(ContentType.Application.Json)
            setBody(mapOf("email" to email, "password" to password))
        }

        val body = response.bodyAsText()
        if (!response.status.isSuccess()) {
            throw Exception(AuthErrorMapper.map(body))
        }

        val authResponse = kotlinx.serialization.json.Json {
            ignoreUnknownKeys = true
            isLenient = true
        }.decodeFromString(AuthResponse.serializer(), body)

        return authResponse.toDomain()
    }

    suspend fun signIn(email: String, password: String): User {
        val response = client.post("${SupabaseConfig.BASE_URL}/auth/v1/token?grant_type=password") {
            header("apikey", SupabaseConfig.ANON_KEY)
            contentType(ContentType.Application.Json)
            setBody(mapOf("email" to email, "password" to password))
        }

        val body = response.bodyAsText()
        if (!response.status.isSuccess()) {
            throw Exception(AuthErrorMapper.map(body))
        }

        val authResponse = kotlinx.serialization.json.Json {
            ignoreUnknownKeys = true
            isLenient = true
        }.decodeFromString(AuthResponse.serializer(), body)

        return authResponse.toDomain()
    }
}

