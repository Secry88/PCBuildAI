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
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.pcbuildai.domain.models.Profile
import com.example.pcbuildai.presentation.navigation.BottomNavScreen
import com.example.pcbuildai.presentation.navigation.MainContentNavGraph
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(
    userId: String,
    onNavigateToAuth: () -> Unit,
    navController: NavController
) {
    val bottomBarNavController = rememberNavController()

    Scaffold(
        bottomBar = {

            BottomBar(navController = bottomBarNavController)
        }
    ) { innerPadding ->

        MainContentNavGraph(
            bottomBarNavController = bottomBarNavController,
            onNavigateToUpdateProfile = { profile: Profile ->

                val name = URLEncoder.encode(profile.name ?: "", StandardCharsets.UTF_8.name())
                val surname = URLEncoder.encode(profile.surname ?: "", StandardCharsets.UTF_8.name())
                val phone = URLEncoder.encode(profile.phoneNumber ?: "", StandardCharsets.UTF_8.name())

                navController.navigate("update_profile/$userId?name=$name&surname=$surname&phone=$phone")
            },
            userId = userId
        )
    }
}

@Composable
fun BottomBar(
    navController: NavHostController,
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
