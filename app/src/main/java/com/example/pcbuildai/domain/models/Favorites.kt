package com.example.pcbuildai.domain.models

import kotlinx.datetime.Instant
import java.util.UUID
@OptIn(kotlin.time.ExperimentalTime::class)
data class Favorites(
    val id: UUID,
    val userId: UUID,
    val buildId: UUID,
    val createdAt: Instant,
    )
