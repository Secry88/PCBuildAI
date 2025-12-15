// presentation/favorites/FavoritesViewModel.kt
package com.example.pcbuildai.presentation.favorites

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pcbuildai.data.repository.FavoritesRepositoryImpl
import com.example.pcbuildai.domain.models.Build
import com.example.pcbuildai.domain.models.Components
import com.example.pcbuildai.domain.repository.BuildRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val favoritesRepository: FavoritesRepositoryImpl,
    private val buildRepository: BuildRepository
) : ViewModel() {

    // Используем StateFlow вместо mutableStateOf для ViewModel
    private val _state = MutableStateFlow(FavoritesState())
    val state: StateFlow<FavoritesState> = _state.asStateFlow()

    var userId: String? = null

    // presentation/favorites/FavoritesViewModel.kt - обновляем loadFavorites
    fun loadFavorites() {
        val currentUserId = userId ?: return

        println("DEBUG FavoritesViewModel: Loading favorites for userId: $currentUserId")

        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)
            try {
                println("DEBUG: Calling favoritesRepository.getFavoriteBuilds...")

                // Загружаем избранные сборки
                val favorites = favoritesRepository.getFavoriteBuilds(currentUserId)
                println("DEBUG: Got ${favorites.size} favorite builds")

                if (favorites.isNotEmpty()) {
                    println("DEBUG: First favorite build id: ${favorites.first().id}, comment: ${favorites.first().comment}")
                }

                // Для каждой сборки загружаем компоненты
                val componentsMap = mutableMapOf<String, List<Components>>()

                favorites.forEach { build ->
                    try {
                        println("DEBUG: Loading components for build ${build.id}")
                        val components = buildRepository.getComponentsByBuild(build.id.toString())
                        println("DEBUG: Got ${components.size} components for build ${build.id}")
                        // Используем id как строку для ключа
                        componentsMap[build.id.toString()] = components
                    } catch (e: Exception) {
                        println("DEBUG: Error loading components for build ${build.id}: ${e.message}")
                        // Если не удалось загрузить компоненты, оставляем пустой список
                        componentsMap[build.id.toString()] = emptyList()
                    }
                }

                _state.value = _state.value.copy(
                    isLoading = false,
                    favorites = favorites,
                    componentsMap = componentsMap
                )
                println("DEBUG: State updated successfully")

            } catch (e: Exception) {
                println("DEBUG: ERROR in loadFavorites: ${e.message}")
                e.printStackTrace()
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = e.message ?: "Ошибка загрузки избранного"
                )
            }
        }
    }

    fun removeFromFavorites(buildId: String) {
        val currentUserId = userId ?: return

        viewModelScope.launch {
            try {
                favoritesRepository.removeFromFavorites(buildId, currentUserId)
                loadFavorites() // Перезагружаем список после удаления
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    error = e.message ?: "Ошибка удаления из избранного"
                )
            }
        }
    }
}

data class FavoritesState(
    val isLoading: Boolean = false,
    val favorites: List<Build> = emptyList(),
    val componentsMap: Map<String, List<Components>> = emptyMap(),
    val error: String? = null
)