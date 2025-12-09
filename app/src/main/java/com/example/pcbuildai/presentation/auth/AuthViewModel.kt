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

data class LoginState(
    val isLoading: Boolean = false,
    val user: User? = null,
    val error: String? = null
)

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val useCases: AuthUseCases
) : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state

    fun signIn(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            _state.value = LoginState(error = "Заполните все поля")
            return
        }

        _state.value = LoginState(isLoading = true)

        viewModelScope.launch {
            try {
                val user = useCases.signInUseCase(email, password)
                _state.value = LoginState(user = user)
            } catch (e: Exception) {
                _state.value = LoginState(error = e.message ?: "Ошибка авторизации")
            }
        }
    }
}
