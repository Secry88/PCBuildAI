package com.example.pcbuildai.presentation.navigation

import UpdateProfileScreen
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pcbuildai.presentation.auth.AuthScreen
import com.example.pcbuildai.presentation.register.RegistrationScreen
import com.example.pcbuildai.presentation.main.MainScreen
import java.util.UUID
import com.example.pcbuildai.domain.models.Profile

@Composable
fun AppNavGraph() {

    val rootNavController = rememberNavController()
    val bottomNavController = rememberNavController()

    NavHost(
        navController = rootNavController,
        startDestination = "auth"
    ) {

        composable("auth") {
            AuthScreen(
                onLoginSuccess = { userId ->
                    rootNavController.navigate("main/$userId")
                },
                onNavigateToRegister = {
                    rootNavController.navigate("register")
                }
            )
        }

        composable("register") {
            RegistrationScreen(
                onRegisterSuccess = {
                    rootNavController.navigate("auth")
                },
                onNavigateBack = {
                    rootNavController.popBackStack()
                }
            )
        }

        composable(
            route = "main/{userId}",
            arguments = listOf(
                navArgument("userId") { type = NavType.StringType }
            )
        ) { entry ->
            val userId = entry.arguments?.getString("userId")!!

            MainScreen(
                userId = userId,
                navController = rootNavController,
                bottomNavController = bottomNavController
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
        ) { entry ->

            val userId = entry.arguments?.getString("userId") ?: return@composable
            val name = entry.arguments?.getString("name")
            val surname = entry.arguments?.getString("surname")
            val phone = entry.arguments?.getString("phone")

            UpdateProfileScreen(
                profile = Profile(
                    id = UUID.fromString(userId),
                    email = "",
                    name = name,
                    surname = surname,
                    phoneNumber = phone,
                    avatar = null
                ),
                userId = userId,
                onDone = {
                    rootNavController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.set("should_refresh", true)

                    rootNavController.popBackStack()
                },
                onCancel = {
                    rootNavController.popBackStack()
                }
            )
        }
    }
}
