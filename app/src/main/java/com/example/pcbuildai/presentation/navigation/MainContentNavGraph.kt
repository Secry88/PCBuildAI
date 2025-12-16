package com.example.pcbuildai.presentation.navigation

import ProfileScreen
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.pcbuildai.domain.models.Profile
import com.example.pcbuildai.presentation.favorites.FavoritesScreen
import com.example.pcbuildai.presentation.history.HistoryScreen
import com.example.pcbuildai.presentation.main.HomeScreen

@Composable
fun MainContentNavGraph(
    bottomBarNavController: NavHostController,
    userId: String,
    onNavigateToUpdateProfile: (Profile) -> Unit,
    onLogout: () -> Unit
) {
    NavHost(
        navController = bottomBarNavController,
        startDestination = BottomNavScreen.Home.route
    ) {
        composable(BottomNavScreen.Home.route) {
            HomeScreen(userId = userId)
        }
        composable(BottomNavScreen.Favorites.route) {
            FavoritesScreen(userId = userId)
        }
        composable(BottomNavScreen.Profile.route) {
            ProfileScreen(
                userId = userId,
                onNavigateToUpdateProfile = onNavigateToUpdateProfile,
                onLogoutClick = onLogout
            )
        }
        composable(BottomNavScreen.History.route) {
            HistoryScreen(userId = userId)
        }
    }


}