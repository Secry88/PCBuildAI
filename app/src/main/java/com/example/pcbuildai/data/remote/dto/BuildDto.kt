package com.example.pcbuildai.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BuildDto(
    val id: String,
    @SerialName("created_at") val createdAt: String,
    @SerialName("user_id") val userId: String,
    val budget: Float,
    val purpose: String,
    val wishes: String,
    val comments: String,
)
