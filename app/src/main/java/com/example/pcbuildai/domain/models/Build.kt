package com.example.pcbuildai.domain.models

import kotlinx.datetime.Instant
import java.util.UUID

@OptIn(kotlin.time.ExperimentalTime::class)
data class Build(
    val id: UUID,
    val createdAt: Instant,
    val userId: UUID,
    val comment: String,
    val totalPrice: Float
)
