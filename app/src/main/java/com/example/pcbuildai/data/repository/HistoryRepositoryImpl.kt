// data/repository/HistoryRepositoryImpl.kt
package com.example.pcbuildai.data.repository

import com.example.pcbuildai.data.remote.services.HistoryService
import com.example.pcbuildai.domain.models.Build
import com.example.pcbuildai.domain.repository.HistoryRepository
import toDomain
import javax.inject.Inject

class HistoryRepositoryImpl @Inject constructor(
    private val service: HistoryService
) : HistoryRepository {

    override suspend fun addToHistory(buildId: String, userId: String, budget: Float?) {
        service.addToHistory(buildId, userId, budget)
    }

    override suspend fun getHistory(userId: String): List<Build> {
        return service.getHistory(userId).map { it.toDomain() }
    }

    override suspend fun clearHistory(userId: String) {
        service.clearHistory(userId)
    }
}