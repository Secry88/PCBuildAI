package com.example.pcbuildai.presentation.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pcbuildai.data.repository.FavoritesRepositoryImpl
import com.example.pcbuildai.domain.models.Build
import com.example.pcbuildai.domain.models.Components
import com.example.pcbuildai.domain.repository.BuildRepository
import com.example.pcbuildai.domain.repository.HistoryRepository
import com.example.pcbuildai.domain.usecase.build.GetBuildUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getBuildUseCase: GetBuildUseCase,
    private val historyRepository: HistoryRepository
    private val repository: BuildRepository,
    private val favoritesRepository: FavoritesRepositoryImpl
) : ViewModel() {

    var state by mutableStateOf(HomeState())
        private set

    var currentUserId: String? = null

    fun updateBudget(value: String) {
        state = state.copy(budget = value)
    }

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

                val isFavorite = if (build != null && currentUserId != null) {
                    favoritesRepository.isFavorite(build.id.toString(), currentUserId!!)
                } else {
                    false
                }

                if (build != null && currentUserId != null) {
                    try {
                        historyRepository.addToHistory(
                            buildId = build.id.toString(),
                            userId = currentUserId!!,
                            budget = budgetValue
                        )
                        println("DEBUG: Added to history")
                    } catch (e: Exception) {
                        println("DEBUG: Failed to save to history: ${e.message}")
                    }
                val isFavorite = if (build != null && currentUserId != null) {
                    favoritesRepository.isFavorite(build.id.toString(), currentUserId!!)
                } else {
                    false
                }

                state = state.copy(
                    isLoading = false,
                    build = build,
                    components = components,
                    isFavorite = isFavorite
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

    fun toggleFavorite() {
        val build = state.build ?: return
        val userId = currentUserId ?: return

        viewModelScope.launch {
            try {
                if (state.isFavorite) {
                    favoritesRepository.removeFromFavorites(build.id.toString(), userId)
                } else {
                    favoritesRepository.addToFavorites(build.id.toString(), userId)
                }
                state = state.copy(
                    isFavorite = !state.isFavorite
                )
            } catch (e: Exception) {
                println("DEBUG Favorite Error: ${e.message}")
            }
        }
    }
}

data class HomeState(
    val budget: String = "",
    val isLoading: Boolean = false,
    val build: Build? = null,
    val components: List<Components> = emptyList(),
    val isFavorite: Boolean = false,
    val error: String? = null
)