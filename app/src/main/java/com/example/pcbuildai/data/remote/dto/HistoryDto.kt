package com.example.pcbuildai.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HistoryDto(
    val id: String,
    @SerialName("user_id") val userId: String,
    @SerialName("build_id") val buildId: String,
    @SerialName("viewed_at") val viewedAt: String,
    @SerialName("search_budget") val searchBudget: Float? = null
)
