package com.example.pcbuildai.domain.repository

import com.example.pcbuildai.domain.models.Build

interface HistoryRepository {
    suspend fun addToHistory(buildId: String, userId: String, budget: Float? = null)
    suspend fun getHistory(userId: String): List<Build>
    suspend fun clearHistory(userId: String)
}