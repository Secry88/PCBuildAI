package com.example.pcbuildai.presentation.history

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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val historyRepository: HistoryRepository,
    private val buildRepository: BuildRepository,
    private val favoritesRepository: FavoritesRepositoryImpl
) : ViewModel() {

    private val _state = MutableStateFlow(HistoryState())
    val state: StateFlow<HistoryState> = _state.asStateFlow()

    var userId: String? = null

    fun loadHistory() {
        val currentUserId = userId ?: return

        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)
            try {
                val history = historyRepository.getHistory(currentUserId)
                println("DEBUG: Loaded ${history.size} history items")

                val componentsMap = mutableMapOf<String, List<Components>>()
                val favoritesMap = mutableMapOf<String, Boolean>()

                history.forEach { build ->
                    try {
                        val components = buildRepository.getComponentsByBuild(build.id.toString())
                        componentsMap[build.id.toString()] = components

                        val isFavorite = favoritesRepository.isFavorite(
                            build.id.toString(),
                            currentUserId
                        )
                        favoritesMap[build.id.toString()] = isFavorite

                    } catch (e: Exception) {
                        println("DEBUG: Error loading details for build ${build.id}: ${e.message}")
                    }
                }

                _state.value = _state.value.copy(
                    isLoading = false,
                    history = history,
                    componentsMap = componentsMap,
                    favoritesMap = favoritesMap
                )

            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = e.message ?: "Ошибка загрузки истории"
                )
            }
        }
    }

    fun clearHistory() {
        val currentUserId = userId ?: return

        viewModelScope.launch {
            try {
                historyRepository.clearHistory(currentUserId)
                loadHistory()
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    error = e.message ?: "Ошибка очистки истории"
                )
            }
        }
    }
}

data class HistoryState(
    val isLoading: Boolean = false,
    val history: List<Build> = emptyList(),
    val componentsMap: Map<String, List<Components>> = emptyMap(),
    val favoritesMap: Map<String, Boolean> = emptyMap(),
    val error: String? = null
)
