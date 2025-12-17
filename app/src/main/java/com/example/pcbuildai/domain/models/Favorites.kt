package com.example.pcbuildai.domain.models

import kotlinx.datetime.Instant
import java.util.UUID
@OptIn(kotlin.time.ExperimentalTime::class)
data class Favorites(
    val id: UUID? = null,
    val userId: UUID? = null,
    val buildId: UUID? = null,
    val createdAt: Instant? = null,
    val searchBudget: Float? = null
    )
