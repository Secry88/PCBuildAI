// presentation/main/HomeViewModel.kt
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
import com.example.pcbuildai.domain.usecase.build.GetBuildUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getBuildUseCase: GetBuildUseCase,
    private val repository: BuildRepository,
    private val favoritesRepository: FavoritesRepositoryImpl // Используем impl для доступа к методам с userId
) : ViewModel() {

    var state by mutableStateOf(HomeState())
        private set

    // Для хранения userId (будет устанавливаться из MainScreen)
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

                // Проверяем, в избранном ли сборка
                val isFavorite = if (build != null && currentUserId != null) {
                    favoritesRepository.isFavorite(build.id.toString(), currentUserId!!)
                } else {
                    false
                }

                state = state.copy(
                    isLoading = false,
                    build = build,
                    components = components,
                    isFavorite = isFavorite // Добавляем статус в состояние
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

    // Функция переключения избранного
    fun toggleFavorite() {
        val build = state.build ?: return
        val userId = currentUserId ?: return

        viewModelScope.launch {
            try {
                if (state.isFavorite) {
                    // Удаляем из избранного
                    favoritesRepository.removeFromFavorites(build.id.toString(), userId)
                } else {
                    // Добавляем в избранное
                    favoritesRepository.addToFavorites(build.id.toString(), userId)
                }

                // Обновляем состояние
                state = state.copy(
                    isFavorite = !state.isFavorite
                )
            } catch (e: Exception) {
                println("DEBUG Favorite Error: ${e.message}")
                // Можно добавить обработку ошибки в UI
            }
        }
    }
}

// Обновляем HomeState
data class HomeState(
    val budget: String = "",
    val isLoading: Boolean = false,
    val build: Build? = null,
    val components: List<Components> = emptyList(),
    val isFavorite: Boolean = false, // Новое поле!
    val error: String? = null
)