package com.example.pcbuildai.domain.repository

import com.example.pcbuildai.domain.models.Build

interface FavoritesRepository {
    suspend fun addToFavorites(buildId: String)
    suspend fun removeFromFavorites(buildId: String)
    suspend fun isFavorite(buildId: String): Boolean
    suspend fun getFavoriteBuilds(): List<Build>
}