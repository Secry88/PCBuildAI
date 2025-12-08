package com.example.pcbuildai.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pcbuildai.presentation.auth.AuthScreen
import com.example.pcbuildai.presentation.auth.RegistrationScreen
import com.example.pcbuildai.presentation.main.MainScreen

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "auth") {
        composable("auth") {
            AuthScreen(
                onLoginSuccess = { navController.navigate("main") },
                onNavigateToRegister = { navController.navigate("registration") }
            )
        }

        composable("registration") {
            RegistrationScreen(
                onRegisterSuccess = { navController.navigate("main") },
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable("main") {
            MainScreen()
        }
    }
}

