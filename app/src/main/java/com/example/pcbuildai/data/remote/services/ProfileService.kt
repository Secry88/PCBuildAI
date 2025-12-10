package com.example.pcbuildai.data.remote.services

import com.example.pcbuildai.data.remote.SupabaseConfig
import com.example.pcbuildai.data.remote.dto.ProfileDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header

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
}
