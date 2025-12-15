// presentation/navigation/BottomNavScreen.kt
package com.example.pcbuildai.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavScreen(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Home : BottomNavScreen(
        route = "home",
        title = "Главная",
        icon = Icons.Default.Home
    )

    object Favorites : BottomNavScreen( // Добавляем этот экран
        route = "favorites",
        title = "Избранное",
        icon = Icons.Default.Favorite
    )

    object Profile : BottomNavScreen(
        route = "profile",
        title = "Профиль",
        icon = Icons.Default.Person
    )
}