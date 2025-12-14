package com.example.pcbuildai.data.repository

import com.example.pcbuildai.data.remote.services.BuildService
import com.example.pcbuildai.domain.models.Build
import com.example.pcbuildai.domain.models.Components
import com.example.pcbuildai.domain.repository.BuildRepository
import toDomain

class BuildRepositoryImpl(
    private val service: BuildService
) : BuildRepository {

    override suspend fun getAllBuilds(): List<Build> =
        service.getAllBuilds().map { it.toDomain() }

    override suspend fun getComponentsByBuild(buildId: String): List<Components> =
        service.getComponentsByBuild(buildId).map { it.toDomain() }
}
