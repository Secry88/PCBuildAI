package com.example.pcbuildai.Data.Remote.Dto

import com.example.pcbuildai.Domain.Models.Components
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.Date
import java.util.UUID
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
