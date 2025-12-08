package com.example.pcbuildai.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FavoritesDto(
    val id: String,
    @SerialName("user_id") val userId: String,
    @SerialName("build_id") val buildId: String,
    @SerialName("created_at") val createdAt: String
)
