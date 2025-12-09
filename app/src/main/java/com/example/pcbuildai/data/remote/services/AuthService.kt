package com.example.pcbuildai.data.remote.services

import com.example.pcbuildai.data.remote.SupabaseConfig
import com.example.pcbuildai.data.remote.dto.AuthResponse
import com.example.pcbuildai.domain.models.User
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import toDomain

class AuthService(private val client: HttpClient) {
    suspend fun signUp(email: String, password :String): User{
        val response = client.post("${SupabaseConfig.BASE_URL}/auth/v1/signup"){
            header("apikey", SupabaseConfig.ANON_KEY)
            contentType(ContentType.Application.Json)
            setBody(mapOf("email" to email, "password" to password))
        }
        return response.body<AuthResponse>().toDomain()
    }
    suspend fun signIn(email: String, password: String): User {
        val response = client.post("${SupabaseConfig.BASE_URL}/auth/v1/token") {
            header("apikey", SupabaseConfig.ANON_KEY)
            contentType(ContentType.Application.Json)

            setBody(
                buildJsonObject {
                    put("email", email)
                    put("password", password)
                    put("grant_type", "password")
                }
            )
        }
        return response.body<AuthResponse>().toDomain()
    }
}
