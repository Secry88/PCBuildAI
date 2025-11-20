package com.example.pcbuildai.Domain.Models

import java.util.Date
import java.util.UUID

data class Favorites(
    val id: UUID,
    val userId: UUID,
    val buildId: UUID,
    val createdAt: Date,
    )
