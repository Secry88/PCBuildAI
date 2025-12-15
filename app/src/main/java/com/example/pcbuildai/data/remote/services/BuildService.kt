package com.example.pcbuildai.data.remote.services

import com.example.pcbuildai.data.remote.SupabaseConfig
import com.example.pcbuildai.data.remote.dto.BuildDto
import com.example.pcbuildai.data.remote.dto.ComponentsDto
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.statement.bodyAsText
import io.ktor.http.isSuccess
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject

class BuildService(
    private val client: HttpClient
) {

    private val json = Json { ignoreUnknownKeys = true }

    suspend fun getAllBuilds(): List<BuildDto> {
        val response = client.get(
            "${SupabaseConfig.BASE_URL}/rest/v1/Builds?select=*"
        ) {
            header("apikey", SupabaseConfig.ANON_KEY)
            header("Authorization", "Bearer ${SupabaseConfig.ANON_KEY}")
        }

        if (!response.status.isSuccess()) {
            throw Exception("Ошибка загрузки сборок")
        }

        return json.decodeFromString(
            ListSerializer(BuildDto.serializer()),
            response.bodyAsText()
        )
    }

    suspend fun getComponentsByBuild(buildId: String): List<ComponentsDto> {
        val response = client.get(
            "${SupabaseConfig.BASE_URL}/rest/v1/Builds_Components?select=Components(*)&Build_id=eq.$buildId"
        ) {
            header("apikey", SupabaseConfig.ANON_KEY)
            header("Authorization", "Bearer ${SupabaseConfig.ANON_KEY}")
        }

        if (!response.status.isSuccess()) {
            throw Exception("Ошибка загрузки компонентов")
        }

        val rawResponse = response.bodyAsText()
        println("DEBUG RAW RESPONSE: $rawResponse")

        return json.parseToJsonElement(rawResponse)
            .jsonArray
            .mapNotNull {
                it.jsonObject["Components"]?.let { componentJson ->
                    try {
                        json.decodeFromJsonElement(
                            ComponentsDto.serializer(),
                            componentJson
                        )
                    } catch (e: Exception) {
                        println("DEBUG: Error parsing component: ${e.message}")
                        null
                    }
                }
            }
    }

}
