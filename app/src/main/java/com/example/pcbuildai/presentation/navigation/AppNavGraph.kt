package com.example.pcbuildai.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pcbuildai.presentation.auth.AuthScreen
import com.example.pcbuildai.presentation.register.RegistrationScreen
import com.example.pcbuildai.presentation.main.MainScreen

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "auth") {

        composable("auth") {
            AuthScreen(
                onLoginSuccess = { navController.navigate("main") },
                onNavigateToRegister = { navController.navigate("register") }
            )
        }

        composable("register") {
            RegistrationScreen(
                onRegisterSuccess = { navController.navigate("auth") },
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable("main") { MainScreen() }
    }

}

