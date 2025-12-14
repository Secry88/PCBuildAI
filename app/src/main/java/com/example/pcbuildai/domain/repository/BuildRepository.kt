package com.example.pcbuildai.domain.repository

import com.example.pcbuildai.domain.models.Build
import com.example.pcbuildai.domain.models.Components

interface BuildRepository {
    suspend fun getAllBuilds(): List<Build>
    suspend fun getComponentsByBuild(buildId: String): List<Components>
}
