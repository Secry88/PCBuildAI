package com.example.pcbuildai.Domain.Models

import java.util.UUID

data class Profile(
    val id: UUID,
    val name: String?,
    val surname: String?,
    val avatar: String?,
    val phoneNumber: String?
)
