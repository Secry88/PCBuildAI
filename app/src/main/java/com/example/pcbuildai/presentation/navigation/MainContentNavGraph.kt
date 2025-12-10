// Файл: presentation/navigation/MainContentNavGraph.kt

package com.example.pcbuildai.presentation.navigation

import ProfileScreen
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.pcbuildai.presentation.main.HomeScreen

@Composable
fun MainContentNavGraph(
    navController: NavHostController,
    userId: String // <-- ПРИНИМАЕМ userId ЗДЕСЬ
) {
    NavHost(
        navController = navController,
        startDestination = BottomNavScreen.Home.route
    ) {
        composable(route = BottomNavScreen.Home.route) {
            HomeScreen()
        }
        composable(route = BottomNavScreen.Profile.route) {
            // ИСПОЛЬЗУЕМ РЕАЛЬНЫЙ userId ВМЕСТО ЗАГЛУШКИ
            ProfileScreen(userId = userId)
        }
    }
}
