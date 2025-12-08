package com.example.pcbuildai.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pcbuildai.domain.models.User
import com.example.pcbuildai.domain.usecase.auth.AuthUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AuthState(
    val isLoading: Boolean = false,
    val user: User? = null,
    val error: String? = null
)

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val useCases: AuthUseCases
) : ViewModel() {

    private val _state = MutableStateFlow(AuthState())
    val state: StateFlow<AuthState> = _state

    fun signUp(email: String, password: String) {
        _state.value = AuthState(isLoading = true)
        viewModelScope.launch {
            try {
                val user = useCases.signUpUseCase(email, password)
                _state.value = AuthState(user = user) // Навигация на MainScreen через state.user
            } catch (e: Exception) {
                _state.value = AuthState(error = e.message)
            }
        }
    }

    fun signIn(email: String, password: String) {
        _state.value = AuthState(isLoading = true)
        viewModelScope.launch {
            try {
                val user = useCases.signInUseCase(email, password)
                _state.value = AuthState(user = user)
            } catch (e: Exception) {
                _state.value = AuthState(error = e.message)
            }
        }
    }
}
