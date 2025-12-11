package com.example.pcbuildai.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProfileDto(
    val id: String,
    val name: String?,
    val email: String,
    val surname: String?,
    val avatar: String?,
    @SerialName("phone_number") val phoneNumber: String?
)
