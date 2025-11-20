package com.example.pcbuildai.Domain.Models

import java.util.Date
import java.util.UUID

data class Build(
    val id: UUID,
    val createdAt: Date,
    val userId: UUID,
    val budget: Float,
    val purpose: String,
    val wishes: String,
    val comments: String,
    val components: List<Components>
)
