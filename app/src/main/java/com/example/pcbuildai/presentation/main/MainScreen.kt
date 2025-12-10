// Файл: presentation/main/MainScreen.kt
package com.example.pcbuildai.presentation.main

import android.annotation.SuppressLint
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.pcbuildai.presentation.navigation.BottomNavScreen
import com.example.pcbuildai.presentation.navigation.MainContentNavGraph

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(
    // ШАГ 1: ПРИНИМАЕМ userId ЗДЕСЬ
    userId: String,
    onNavigateToAuth: () -> Unit
) {
    val mainContentNavController = rememberNavController()

    Scaffold(
        bottomBar = {
            // ШАГ 2: ПЕРЕДАЕМ userId В BottomBar
            BottomBar(navController = mainContentNavController, userId = userId)
        }
    ) { innerPadding ->
        // ШАГ 3: ПЕРЕДАЕМ userId В ГРАФ НАВИГАЦИИ
        MainContentNavGraph(navController = mainContentNavController, userId = userId)
    }
}

@Composable
fun BottomBar(
    // Параметры в правильном порядке
    navController: NavHostController,
    userId: String
) {
    val screens = listOf(
        BottomNavScreen.Home,
        BottomNavScreen.Profile,
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar {
        screens.forEach { screen ->
            NavigationBarItem(
                label = { Text(text = screen.title) },
                icon = { Icon(imageVector = screen.icon, contentDescription = "Navigation Icon") },
                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                onClick = {
                    // При клике мы просто переходим по внутреннему маршруту.
                    // userId здесь для навигации не нужен, он уже проброшен в граф.
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}
