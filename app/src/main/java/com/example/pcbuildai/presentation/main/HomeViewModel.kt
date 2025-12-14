package com.example.pcbuildai.presentation.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pcbuildai.domain.models.Build
import com.example.pcbuildai.domain.models.Components
import com.example.pcbuildai.domain.repository.BuildRepository
import com.example.pcbuildai.domain.usecase.build.GetBuildUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getBuildUseCase: GetBuildUseCase,
    private val repository: BuildRepository
) : ViewModel() {

    var state by mutableStateOf(HomeState())
        private set

    fun updateBudget(value: String) {
        state = state.copy(budget = value)
    }

    // HomeViewModel.kt
    fun findBuild() {
        val budgetValue = state.budget.toFloatOrNull() ?: return

        viewModelScope.launch {
            state = state.copy(isLoading = true, error = null)

            try {
                val build = getBuildUseCase.execute(budgetValue)
                println("DEBUG: Found build: ${build?.comment}, ID: ${build?.id}")

                val components = build?.let {
                    repository.getComponentsByBuild(it.id.toString())
                } ?: emptyList()

                println("DEBUG: Received ${components.size} components")
                components.forEachIndexed { index, component ->
                    println("DEBUG Component $index: ${component.name} - ${component.price}")
                }

                state = state.copy(
                    isLoading = false,
                    build = build,
                    components = components
                )
            } catch (e: Exception) {
                println("DEBUG Error: ${e.message}")
                e.printStackTrace()
                state = state.copy(
                    isLoading = false,
                    error = e.message ?: "Ошибка загрузки"
                )
            }
        }
    }
}

data class HomeState(
    val budget: String = "",
    val isLoading: Boolean = false,
    val build: Build? = null,
    val components: List<Components> = emptyList(),
    val error: String? = null
)
