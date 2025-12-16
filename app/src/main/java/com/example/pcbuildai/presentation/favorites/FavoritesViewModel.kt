package com.example.pcbuildai.presentation.favorites

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

    private val _state = MutableStateFlow(FavoritesState())
    val state: StateFlow<FavoritesState> = _state.asStateFlow()

    var userId: String? = null

    fun loadFavorites() {
        val currentUserId = userId ?: return

        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)
            try {

                val favorites = favoritesRepository.getFavoriteBuilds(currentUserId)

                val componentsMap = mutableMapOf<String, List<Components>>()
                favorites.forEach { build ->
                    try {
                        val components = buildRepository.getComponentsByBuild(build.id.toString())
                        componentsMap[build.id.toString()] = components
                    } catch (e: Exception) {
                        componentsMap[build.id.toString()] = emptyList()
                    }
                }

                _state.value = _state.value.copy(
                    isLoading = false,
                    favorites = favorites,
                    componentsMap = componentsMap
                )

            } catch (e: Exception) {
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
                loadFavorites()
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