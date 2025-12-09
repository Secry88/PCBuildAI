package com.example.pcbuildai.data.remote

import io.ktor.client.plugins.logging.*
import android.util.Log
import io.ktor.client.engine.okhttp.*
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import okhttp3.logging.HttpLoggingInterceptor

object SupabaseClient {
    val supabaseClient = HttpClient(OkHttp) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
            })
        }

        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    Log.d("KTOR_LOGGER", message)
                }
            }
            level = LogLevel.ALL
        }

        engine {
            config {
                followRedirects(true)
            }

            addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
        }

        expectSuccess = false
    }
}

