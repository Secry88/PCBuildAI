package com.example.pcbuildai.presentation.main

import android.annotation.SuppressLint
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.pcbuildai.domain.models.Profile
import com.example.pcbuildai.presentation.navigation.BottomNavScreen
import com.example.pcbuildai.presentation.navigation.MainContentNavGraph
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(
    userId: String,
    navController: NavController,
    bottomNavController: NavHostController,
    onLogout: () -> Unit
) {

    val homeViewModel = hiltViewModel<HomeViewModel>()

    LaunchedEffect(userId) {
        homeViewModel.currentUserId = userId
    }

    Scaffold(
        bottomBar = {
            BottomBar(navController = bottomNavController)
        }
    ) { innerPadding ->

        MainContentNavGraph(
            bottomBarNavController = bottomNavController,
            userId = userId,
            onNavigateToUpdateProfile = { profile: Profile ->

                val name = URLEncoder.encode(profile.name ?: "", StandardCharsets.UTF_8.name())
                val surname = URLEncoder.encode(profile.surname ?: "", StandardCharsets.UTF_8.name())
                val phone = URLEncoder.encode(profile.phoneNumber ?: "", StandardCharsets.UTF_8.name())

                navController.navigate(
                    "update_profile/$userId?name=$name&surname=$surname&phone=$phone"
                )
            },
            onLogout = onLogout
        )
    }
}

@Composable
fun BottomBar(
    navController: NavHostController
) {
    val screens = listOf(
        BottomNavScreen.Home,
        BottomNavScreen.Favorites,
        BottomNavScreen.Profile,
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar {
        screens.forEach { screen ->
            NavigationBarItem(
                label = { Text(screen.title) },
                icon = { Icon(screen.icon, contentDescription = null) },
                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.startDestinationId) {
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