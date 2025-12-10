package com.example.pcbuildai.presentation.navigation

import ProfileScreen
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pcbuildai.presentation.auth.AuthScreen
import com.example.pcbuildai.presentation.register.RegistrationScreen
import com.example.pcbuildai.presentation.main.MainScreen

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "auth") {

        composable("auth") {
            AuthScreen(
                onLoginSuccess = { userId -> navController.navigate("main/$userId") },
                onNavigateToRegister = { navController.navigate("register") }
            )
        }

        composable("register") {
            RegistrationScreen(
                onRegisterSuccess = { navController.navigate("auth") },
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(
            route = "main/{userId}", // Маршрут теперь принимает аргумент
            arguments = listOf(navArgument("userId") { type = NavType.StringType })
        ) { backStackEntry ->
            // Извлекаем userId из аргументов
            val userId = backStackEntry.arguments?.getString("userId")
            if (userId == null) {
                // Если ID не пришел, возвращаемся на экран авторизации
                // (можно добавить обработку ошибки)
                navController.navigate("auth") { popUpTo(0) }
                return@composable
            }

            MainScreen(
                userId = userId, // <-- Передаем userId в MainScreen
                onNavigateToAuth = {
                    navController.navigate("auth") {
                        popUpTo(0)
                    }
                }
            )
        }

    }



}

