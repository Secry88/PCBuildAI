package com.example.pcbuildai.data.repository

import com.example.pcbuildai.data.remote.services.FavoritesService
import com.example.pcbuildai.domain.models.Build
import com.example.pcbuildai.domain.repository.FavoritesRepository
import toDomain
import javax.inject.Inject

class FavoritesRepositoryImpl @Inject constructor(
    private val service: FavoritesService
) : FavoritesRepository {

    override suspend fun addToFavorites(buildId: String) {
        throw NotImplementedError("Используйте перегруженный метод с userId")
    }

    suspend fun addToFavorites(buildId: String, userId: String) {
        service.addToFavorites(buildId, userId)
    }

    override suspend fun removeFromFavorites(buildId: String) {
        throw NotImplementedError("Используйте перегруженный метод с userId")
    }

    suspend fun removeFromFavorites(buildId: String, userId: String) {
        service.removeFromFavorites(buildId, userId)
    }

    override suspend fun isFavorite(buildId: String): Boolean {
        throw NotImplementedError("Используйте перегруженный метод с userId")
    }

    suspend fun isFavorite(buildId: String, userId: String): Boolean {
        return service.isFavorite(buildId, userId)
    }

    override suspend fun getFavoriteBuilds(): List<Build> {
        throw NotImplementedError("Используйте перегруженный метод с userId")
    }

    suspend fun getFavoriteBuilds(userId: String): List<Build> {
        return service.getFavoriteBuilds(userId).map { it.toDomain() }
    }
}