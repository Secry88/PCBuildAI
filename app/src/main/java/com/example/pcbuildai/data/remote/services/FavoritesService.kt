// data/remote/services/FavoritesService.kt
package com.example.pcbuildai.data.remote.services

import com.example.pcbuildai.data.remote.SupabaseConfig
import com.example.pcbuildai.data.remote.dto.BuildDto
import com.example.pcbuildai.data.remote.dto.FavoritesDto
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
import kotlin.collections.isNotEmpty

class FavoritesService(
    private val client: HttpClient
) {
    private val json = Json { ignoreUnknownKeys = true }

    suspend fun addToFavorites(buildId: String, userId: String) {
        val response = client.post(
            "${SupabaseConfig.BASE_URL}/rest/v1/Favorites"
        ) {
            header("apikey", SupabaseConfig.ANON_KEY)
            header("Authorization", "Bearer ${SupabaseConfig.ANON_KEY}")
            header("Prefer", "return=minimal")
            contentType(ContentType.Application.Json)
            setBody(
                mapOf(
                    "build_id" to buildId,
                    "user_id" to userId
                )
            )
        }

        if (!response.status.isSuccess()) {
            throw Exception("Ошибка добавления в избранное: ${response.status}")
        }
    }

    suspend fun removeFromFavorites(buildId: String, userId: String) {
        val response = client.delete(
            "${SupabaseConfig.BASE_URL}/rest/v1/Favorites" +
                    "?build_id=eq.$buildId" +
                    "&user_id=eq.$userId"
        ) {
            header("apikey", SupabaseConfig.ANON_KEY)
            header("Authorization", "Bearer ${SupabaseConfig.ANON_KEY}")
            header("Prefer", "return=minimal")
        }

        if (!response.status.isSuccess()) {
            throw Exception("Ошибка удаления из избранного: ${response.status}")
        }
    }

    suspend fun isFavorite(buildId: String, userId: String): Boolean {
        val response = client.get(
            "${SupabaseConfig.BASE_URL}/rest/v1/Favorites" +
                    "?build_id=eq.$buildId" +
                    "&user_id=eq.$userId" +
                    "&select=id"
        ) {
            header("apikey", SupabaseConfig.ANON_KEY)
            header("Authorization", "Bearer ${SupabaseConfig.ANON_KEY}")
        }

        if (!response.status.isSuccess()) {
            throw Exception("Ошибка проверки избранного: ${response.status}")
        }

        val data = json.decodeFromString<List<FavoritesDto>>(response.bodyAsText())
        return data.isNotEmpty()
    }

    // data/remote/services/FavoritesService.kt - добавляем логи
    suspend fun getFavoriteBuilds(userId: String): List<BuildDto> {
        println("DEBUG FavoritesService: Getting favorites for userId: $userId")

        val url = "${SupabaseConfig.BASE_URL}/rest/v1/Favorites" +
                "?select=Builds(*)" +
                "&user_id=eq.$userId" +
                "&order=created_at.desc"

        println("DEBUG FavoritesService: URL = $url")

        val response = client.get(url) {
            header("apikey", SupabaseConfig.ANON_KEY)
            header("Authorization", "Bearer ${SupabaseConfig.ANON_KEY}")
        }

        println("DEBUG FavoritesService: Response status: ${response.status}")

        if (!response.status.isSuccess()) {
            val errorBody = response.bodyAsText()
            println("DEBUG FavoritesService: Error body: $errorBody")
            throw Exception("Ошибка загрузки избранного: ${response.status}")
        }

        val responseBody = response.bodyAsText()
        println("DEBUG FavoritesService: Response body: $responseBody")

        // Парсим вложенную структуру: [{Builds: {...}}, {Builds: {...}}]
        try {
            val jsonElement = json.parseToJsonElement(responseBody)
            println("DEBUG FavoritesService: Parsed JSON successfully")

            val result = jsonElement.jsonArray.mapNotNull { item ->
                item.jsonObject["Builds"]?.let { buildJson ->
                    json.decodeFromJsonElement(BuildDto.serializer(), buildJson)
                }
            }

            println("DEBUG FavoritesService: Parsed ${result.size} builds")
            return result

        } catch (e: Exception) {
            println("DEBUG FavoritesService: Parse error: ${e.message}")
            e.printStackTrace()
            throw e
        }
    }
}