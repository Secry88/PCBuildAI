package com.example.pcbuildai.data.repository

import com.example.pcbuildai.data.remote.dto.UpdateProfileDto
import com.example.pcbuildai.data.remote.services.ProfileService
import com.example.pcbuildai.domain.models.Profile
import com.example.pcbuildai.domain.repository.ProfileRepository
import toDomain

class ProfileRepositoryImpl(private val profileService: ProfileService) : ProfileRepository {
    override suspend fun getProfile(userId: String): Profile {
        return profileService.getProfile(userId).toDomain()
    }

    override suspend fun updateProfile(userId: String, profile: Profile): Profile {
        val request = UpdateProfileDto(
            name = profile.name,
            surname = profile.surname,
            avatar = profile.avatar,
            phoneNumber = profile.phoneNumber
        )
        return profileService.updateProfile(userId, request).toDomain()
    }
}