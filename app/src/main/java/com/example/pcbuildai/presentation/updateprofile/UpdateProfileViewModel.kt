package com.example.pcbuildai.presentation.updateprofile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pcbuildai.domain.models.Profile
import com.example.pcbuildai.domain.usecase.auth.UpdateProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class UpdateProfileViewModel @Inject constructor(
    private val updateProfileUseCase: UpdateProfileUseCase
) : ViewModel() {
    var state by mutableStateOf(UpdateProfileState())
        private set
    fun init(profile: Profile) {
        state = state.copy(
            name = profile.name ?: "",
            surname = profile.surname ?: "",
            phoneNumber = profile.phoneNumber ?: "",
            originalProfile = profile
        )
    }

    fun updateName(value: String) {
        state = state.copy(name = value)
    }

    fun updateSurname(value: String) {
        state = state.copy(surname = value)
    }

    fun updatePhone(value: String) {
        state = state.copy(phoneNumber = value)
    }

    fun saveChanges(userId: String) {
        viewModelScope.launch {
            try {
                state = state.copy(isSaving = true)

                val updatedProfile = state.originalProfile!!.copy(
                    name = state.name,
                    surname = state.surname,
                    phoneNumber = state.phoneNumber
                )

                val saved = updateProfileUseCase(userId, updatedProfile)

                state = state.copy(
                    isSaving = false,
                    isSuccess = true,
                    originalProfile = saved
                )
            } catch (e: Exception) {
                state = state.copy(
                    isSaving = false,
                    error = e.message
                )
            }
        }
    }


}

data class UpdateProfileState (
    val name: String = "",
    val surname: String = "",
    val phoneNumber: String = "",
    val isSaving: Boolean = false,
    val isSuccess: Boolean = false,
    val error: String? = null,
    val originalProfile: Profile? = null
)