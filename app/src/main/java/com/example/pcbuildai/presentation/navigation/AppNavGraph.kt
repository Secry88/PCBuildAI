package com.example.pcbuildai.presentation.navigation

import UpdateProfileScreen
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pcbuildai.domain.models.Profile
import com.example.pcbuildai.presentation.auth.AuthScreen
import com.example.pcbuildai.presentation.register.RegistrationScreen
import com.example.pcbuildai.presentation.main.MainScreen
import java.util.UUID

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
            route = "main/{userId}",
            arguments = listOf(navArgument("userId") { type = NavType.StringType })
        ) { backStackEntry ->    val userId = backStackEntry.arguments?.getString("userId") ?: return@composable

            MainScreen(
                userId = userId,
                onNavigateToAuth = {
                    navController.navigate("auth") { popUpTo(0) }
                },
                navController = navController
            )
        }

        composable(
            route = "update_profile/{userId}?name={name}&surname={surname}&phone={phone}",
            arguments = listOf(
                navArgument("userId") { type = NavType.StringType },
                navArgument("name") { type = NavType.StringType; nullable = true },
                navArgument("surname") { type = NavType.StringType; nullable = true },
                navArgument("phone") { type = NavType.StringType; nullable = true }
            )
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: return@composable
            val name = backStackEntry.arguments?.getString("name")
            val surname = backStackEntry.arguments?.getString("surname")
            val phone = backStackEntry.arguments?.getString("phone")
            val userIdAsUUID = UUID.fromString(userId)

            val initialProfile = Profile(
                id = userIdAsUUID,
                email = "",
                name = name,
                surname = surname,
                phoneNumber = phone,
                avatar = null
            )

            UpdateProfileScreen(
                profile = initialProfile,
                userId = userId,
                onDone = {
                    navController.currentBackStackEntry
                        ?.savedStateHandle
                        ?.set("should_refresh", true)
                    navController.popBackStack()
                }
            )
        }
    }
}

