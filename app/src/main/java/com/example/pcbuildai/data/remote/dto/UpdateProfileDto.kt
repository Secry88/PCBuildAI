package com.example.pcbuildai.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpdateProfileDto(
    val name: String? = null,
    val surname: String? = null,
    val avatar: String? = null,
    @SerialName("phone_number") val phoneNumber: String? = null
)
