// data/remote/services/HistoryService.kt
package com.example.pcbuildai.data.remote.services

import com.example.pcbuildai.data.remote.SupabaseConfig
import com.example.pcbuildai.data.remote.dto.BuildDto
import com.example.pcbuildai.data.remote.dto.HistoryDto
import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject

class HistoryService(
    private val client: HttpClient
) {
    private val json = Json { ignoreUnknownKeys = true }

    suspend fun addToHistory(buildId: String, userId: String, budget: Float? = null) {
        val response = client.post(
            "${SupabaseConfig.BASE_URL}/rest/v1/History"
        ) {
            header("apikey", SupabaseConfig.ANON_KEY)
            header("Authorization", "Bearer ${SupabaseConfig.ANON_KEY}")
            header("Prefer", "return=minimal")
            contentType(ContentType.Application.Json)
            setBody(
                mapOf(
                    "build_id" to buildId,
                    "user_id" to userId,
                    "search_budget" to budget
                )
            )
        }

        if (!response.status.isSuccess()) {
            println("DEBUG: Failed to add to history: ${response.status}")
            // Можно не бросать исключение, чтобы не ломать основной поток
        }
    }

    suspend fun getHistory(userId: String): List<BuildDto> {
        val response = client.get(
            "${SupabaseConfig.BASE_URL}/rest/v1/History" +
                    "?select=Builds(*),viewed_at,search_budget" +
                    "&user_id=eq.$userId" +
                    "&order=viewed_at.desc" +
                    "&limit=50" // Ограничиваем количество
        ) {
            header("apikey", SupabaseConfig.ANON_KEY)
            header("Authorization", "Bearer ${SupabaseConfig.ANON_KEY}")
        }

        if (!response.status.isSuccess()) {
            throw Exception("Ошибка загрузки истории")
        }

        // Парсим вложенную структуру
        val jsonElement = json.parseToJsonElement(response.bodyAsText())

        return jsonElement.jsonArray.mapNotNull { item ->
            item.jsonObject["Builds"]?.let { buildJson ->
                json.decodeFromJsonElement(BuildDto.serializer(), buildJson)
            }
        }
    }

    suspend fun clearHistory(userId: String) {
        val response = client.delete(
            "${SupabaseConfig.BASE_URL}/rest/v1/History?user_id=eq.$userId"
        ) {
            header("apikey", SupabaseConfig.ANON_KEY)
            header("Authorization", "Bearer ${SupabaseConfig.ANON_KEY}")
            header("Prefer", "return=minimal")
        }

        if (!response.status.isSuccess()) {
            throw Exception("Ошибка очистки истории")
        }
    }
}