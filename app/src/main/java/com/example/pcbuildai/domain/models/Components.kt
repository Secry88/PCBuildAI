package com.example.pcbuildai.domain.models

import java.util.UUID

data class Components(
    val id: UUID,
    val typeId: String,
    val name: String,
    val price: Float,
    val description: String,
)


