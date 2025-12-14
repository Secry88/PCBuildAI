package com.example.pcbuildai.domain.usecase.build

import com.example.pcbuildai.domain.models.Build
import com.example.pcbuildai.domain.repository.BuildRepository
import kotlin.math.abs

class GetBuildUseCase(
    private val repository: BuildRepository
) {

    suspend fun execute(budget: Float): Build? {
        val builds = repository.getAllBuilds()
        return builds.minByOrNull {
            abs(it.totalPrice - budget)
        }
    }
}
