package com.example.pcbuildai.di

import com.example.pcbuildai.data.remote.services.AuthService
import com.example.pcbuildai.data.repository.AuthRepositoryImpl
import com.example.pcbuildai.domain.repository.AuthRepository
import com.example.pcbuildai.domain.usecase.auth.AuthUseCases
import com.example.pcbuildai.domain.usecase.auth.GetProfileUseCase
import com.example.pcbuildai.domain.usecase.SignInUseCase
import com.example.pcbuildai.domain.usecase.SignUpUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient =
        HttpClient(OkHttp) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                })
            }
            expectSuccess = false
            engine {
                config {
                    followRedirects(true)
                }
            }
        }

    @Provides
    @Singleton
    fun provideAuthService(client: HttpClient) : AuthService =
        AuthService(client)

    @Provides
    @Singleton
    fun provideAuthRepository(service: AuthService) : AuthRepository =
        AuthRepositoryImpl(service)

    @Provides
    @Singleton
    fun provideAuthUseCases(repository: AuthRepository) = AuthUseCases(
        signInUseCase = SignInUseCase(repository),
        signUpUseCase = SignUpUseCase(repository),
        getProfileUseCase = GetProfileUseCase(repository)
    )
}