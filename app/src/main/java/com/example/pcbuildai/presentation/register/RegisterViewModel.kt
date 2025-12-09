package com.example.pcbuildai.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pcbuildai.domain.mapper.AuthErrorMapper
import com.example.pcbuildai.domain.models.User
import com.example.pcbuildai.domain.usecase.auth.AuthUseCases
import com.example.pcbuildai.domain.validation.EmailValidator
import com.example.pcbuildai.domain.validation.PasswordValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class RegisterState(
    val isLoading: Boolean = false,
    val user: User? = null,
    val error: String? = null
)

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val useCases: AuthUseCases
) : ViewModel() {

    private val _state = MutableStateFlow(RegisterState())
    val state: StateFlow<RegisterState> = _state

    fun signUp(email: String, password: String, confirm: String) {
        if (email.isBlank() || password.isBlank() || confirm.isBlank()) {
            _state.value = RegisterState(error = "Заполните все поля")
            return
        }
        EmailValidator.validate(email)?.let {
            _state.value = RegisterState(error = it)
            return
        }
        PasswordValidator.validate(password)?.let {
            _state.value = RegisterState(error = it)
            return
        }
        if (password != confirm) {
            _state.value = RegisterState(error = "Пароли не совпадают")
            return
        }

        _state.value = RegisterState(isLoading = true)

        viewModelScope.launch {
            try {
                val user = useCases.signUpUseCase(email, password)
                _state.value = RegisterState(user = user)
            } catch (e: Exception) {
                val errorMsg = AuthErrorMapper.map(e.message ?: "")
                _state.value = RegisterState(error = errorMsg)
            }
        }
    }

}
