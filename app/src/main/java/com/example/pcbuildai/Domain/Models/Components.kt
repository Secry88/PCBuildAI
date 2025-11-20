package com.example.pcbuildai.Domain.Models

import java.util.UUID

data class Components(
    val id: UUID,
    val type: String,
    val name: String,
    val price: Float,
    val description: String,
)
