package com.example.pcbuildai.data.remote.services

import com.example.pcbuildai.data.remote.SupabaseConfig
import com.example.pcbuildai.data.remote.dto.ProfileDto
import com.example.pcbuildai.data.remote.dto.UpdateProfileDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.patch
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess

class ProfileService(private val client: HttpClient) {
    suspend fun getProfile(userId: String): ProfileDto {
        val response = client.get("${SupabaseConfig.BASE_URL}/rest/v1/Profiles") {
            header("apikey", SupabaseConfig.ANON_KEY)
            header("Authorization", "Bearer ${SupabaseConfig.ANON_KEY}")
            header("Accept", "application/json")
            url {
                parameters.append("id", "eq.$userId")
                parameters.append("select", "*")
            }
        }
        val list = response.body<List<ProfileDto>>()
        return list.firstOrNull() ?: throw Exception("Profile not found")
    }

    suspend fun updateProfile(
        userId: String,
        updateProfileDto: UpdateProfileDto
    ): ProfileDto {
        val response = client.patch("${SupabaseConfig.BASE_URL}/rest/v1/Profiles") {
            header("apikey", SupabaseConfig.ANON_KEY)
            header("Authorization", "Bearer ${SupabaseConfig.ANON_KEY}")
            contentType(ContentType.Application.Json)
            header("Prefer", "return=representation")
            url {
                parameters.append("id", "eq.$userId")
                parameters.append("select", "*")
            }
            setBody(updateProfileDto)
        }
        if (response.status.isSuccess()) {
            val list = response.body<List<ProfileDto>>()
            return list.firstOrNull() ?: throw Exception("Profile not found after update")
        } else {
            val errorBody = response.bodyAsText()
            throw Exception("Failed to update profile: $errorBody")
        }
    }
}
