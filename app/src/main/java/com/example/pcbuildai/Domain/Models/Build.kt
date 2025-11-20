package com.example.pcbuildai.Domain.Models

import kotlinx.datetime.Instant
import java.util.UUID

@OptIn(kotlin.time.ExperimentalTime::class)
data class Build(
    val id: UUID,
    val createdAt: Instant,
    val userId: UUID,
    val budget: Float,
    val purpose: String,
    val wishes: String,
    val comments: String,
    val components: List<Components>
)
