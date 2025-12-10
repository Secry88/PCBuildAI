package com.example.pcbuildai.presentation.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pcbuildai.domain.models.Profile
import com.example.pcbuildai.domain.usecase.auth.AuthUseCases
import com.example.pcbuildai.domain.usecase.auth.GetProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getProfileUseCase: GetProfileUseCase
) : ViewModel() {

    var state by mutableStateOf(ProfileState())
        private set

    fun loadProfile(userId: String) {
        viewModelScope.launch {
            try {
                val profile = getProfileUseCase(userId)
                state = state.copy(
                    profile = profile,
                    isLoading = false
                )
            } catch (e: Exception) {
                state = state.copy(
                    error = e.message,
                    isLoading = false
                )
            }
        }
    }
}

data class ProfileState(
    val profile: Profile? = null,
    val isLoading: Boolean = true,
    val error: String? = null
)
