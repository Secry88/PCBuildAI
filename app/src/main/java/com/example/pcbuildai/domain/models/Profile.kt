package com.example.pcbuildai.domain.models

import java.util.UUID

data class Profile(
    val id: UUID,
    val email: String,
    val name: String?,
    val surname: String?,
    val avatar: String?,
    val phoneNumber: String?
)
