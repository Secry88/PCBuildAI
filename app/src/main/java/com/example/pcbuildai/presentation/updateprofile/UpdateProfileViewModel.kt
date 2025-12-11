package com.example.pcbuildai.presentation.updateprofile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pcbuildai.domain.models.Profile
import com.example.pcbuildai.domain.usecase.auth.UpdateProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateProfileViewModel @Inject constructor(
    private val updateProfileUseCase: UpdateProfileUseCase
) : ViewModel() {

    var state by mutableStateOf(UpdateProfileState())
        private set

    private val _events = MutableSharedFlow<UpdateProfileEvent>()
    val events = _events.asSharedFlow()

    fun init(profile: Profile) {
        state = state.copy(
            name = profile.name ?: "",
            surname = profile.surname ?: "",
            phoneNumber = profile.phoneNumber ?: "",
            originalProfile = profile
        )
    }

    fun updateName(v: String) { state = state.copy(name = v) }
    fun updateSurname(v: String) { state = state.copy(surname = v) }
    fun updatePhone(v: String) { state = state.copy(phoneNumber = v) }

    fun saveChanges(userId: String) {
        viewModelScope.launch {
            state = state.copy(isSaving = true)
            try {
                val updated = state.originalProfile!!.copy(
                    name = state.name,
                    surname = state.surname,
                    phoneNumber = state.phoneNumber
                )

                updateProfileUseCase(userId, updated)

                state = state.copy(isSaving = false)

                _events.emit(UpdateProfileEvent.Success)

            } catch (e: Exception) {
                state = state.copy(isSaving = false, error = e.message)
            }
        }
    }
}

data class UpdateProfileState(
    val name: String = "",
    val surname: String = "",
    val phoneNumber: String = "",
    val isSaving: Boolean = false,
    val error: String? = null,
    val originalProfile: Profile? = null
)

sealed class UpdateProfileEvent {
    object Success : UpdateProfileEvent()
}
