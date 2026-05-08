package com.example.w_jetpack_compose.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavigationItem(
    val route: Route,
    val icon: ImageVector,
    val label: String
) {
    data object Home : BottomNavigationItem(Route.PostList, Icons.Default.Home, "Home")
    data object Search : BottomNavigationItem(Route.Search, Icons.Default.Search, "Search")
    data object Notifications : BottomNavigationItem(Route.Notifications, Icons.Default.Notifications, "Alerts")
    data object Profile : BottomNavigationItem(Route.Profile, Icons.Default.Person, "Profile")
}
