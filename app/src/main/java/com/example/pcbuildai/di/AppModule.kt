package com.example.pcbuildai.di

import com.example.pcbuildai.data.remote.SupabaseClient
import com.example.pcbuildai.data.remote.services.AuthService
import com.example.pcbuildai.data.remote.services.BuildService
import com.example.pcbuildai.data.remote.services.ProfileService
import com.example.pcbuildai.data.repository.AuthRepositoryImpl
import com.example.pcbuildai.data.repository.BuildRepositoryImpl
import com.example.pcbuildai.data.repository.ProfileRepositoryImpl
import com.example.pcbuildai.domain.repository.AuthRepository
import com.example.pcbuildai.domain.repository.BuildRepository
import com.example.pcbuildai.domain.repository.ProfileRepository
import com.example.pcbuildai.domain.usecase.profile.GetProfileUseCase
import com.example.pcbuildai.domain.usecase.auth.SignInUseCase
import com.example.pcbuildai.domain.usecase.auth.SignUpUseCase
import com.example.pcbuildai.domain.usecase.build.GetBuildUseCase
import com.example.pcbuildai.domain.usecase.profile.UpdateProfileUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient {
        return SupabaseClient.supabaseClient
    }

    @Provides
    @Singleton
    fun provideAuthService(client: HttpClient) : AuthService =
        AuthService(client)

    @Provides
    @Singleton
    fun provideAuthRepository(
        authService: AuthService
   ) : AuthRepository =
        AuthRepositoryImpl(authService)

    @Provides
    @Singleton
    fun provideProfileRepository(
        profileService: ProfileService
   ) : ProfileRepository =
        ProfileRepositoryImpl(profileService)

    @Provides
    @Singleton
    fun provideSignInUseCase(repository: AuthRepository): SignInUseCase {
        return SignInUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideSignUpUseCase(repository: AuthRepository): SignUpUseCase {
        return SignUpUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetProfileUseCase(repository: ProfileRepository): GetProfileUseCase {
        return GetProfileUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideUpdateProfileUseCase(repository: ProfileRepository): UpdateProfileUseCase {
        return UpdateProfileUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideProfileService(client: HttpClient): ProfileService =
        ProfileService(client)

    @Provides
    @Singleton
    fun provideBuildRepository(
        service: BuildService
    ): BuildRepository = BuildRepositoryImpl(service)

    @Provides
    @Singleton
    fun provideGetBuildUseCase(
        repository: BuildRepository
    ): GetBuildUseCase =
        GetBuildUseCase(repository)

    @Provides
    @Singleton
    fun provideBuildService(client: HttpClient) : BuildService =
        BuildService(client)

}