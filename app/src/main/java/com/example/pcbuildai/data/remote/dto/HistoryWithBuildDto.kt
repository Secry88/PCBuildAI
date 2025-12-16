package com.example.pcbuildai.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HistoryWithBuildDto(
    @SerialName("Builds") val build: BuildDto,
    @SerialName("viewed_at") val viewedAt: String,
    @SerialName("search_budget") val searchBudget: Float?
)
