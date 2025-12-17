// data/remote/dto/FavoritesDto.kt
package com.example.pcbuildai.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FavoritesDto(
    val id: String? = null,
    @SerialName("build_id") val buildId: String? = null,
    @SerialName("user_id") val userId: String? = null,
    @SerialName("created_at") val createdAt: String? = null
)