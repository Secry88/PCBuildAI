package com.example.pcbuildai.presentation.navigation

import androidx.compose.material.icons.Icons
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

    object Profile : BottomNavScreen(
        route = "profile",
        title = "Профиль",
        icon = Icons.Default.Person
    )
}
