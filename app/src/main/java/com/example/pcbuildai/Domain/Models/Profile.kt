package com.example.pcbuildai.Domain.Models

import java.util.UUID

data class Profile(
    val id: UUID,
    val username: String?,
    val avatarUrl: String?,
    val phoneNumber: String?
)
