package com.example.pcbuildai.Data.Remote.Dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ComponentsDto(
    val id: String,
    val name: String,
    val description: String,
    val price: Float,
    val type: String,
    @SerialName("build_id") val buildId: String
)
