// Файл: presentation/navigation/MainContentNavGraph.kt
package com.example.pcbuildai.presentation.navigation

import ProfileScreen
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.pcbuildai.domain.models.Profile
import com.example.pcbuildai.presentation.main.HomeScreen
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun MainContentNavGraph(
    bottomBarNavController: NavHostController,
    userId: String,
    onNavigateToUpdateProfile: (Profile) -> Unit
) {
    NavHost(
        navController = bottomBarNavController,
        startDestination = BottomNavScreen.Home.route
    ) {
        composable(route = BottomNavScreen.Home.route) {
            HomeScreen()
        }
        composable(route = BottomNavScreen.Profile.route) {
            ProfileScreen(
                userId = userId,
                onNavigateToUpdateProfile = onNavigateToUpdateProfile,
            )
        }
    }
}
