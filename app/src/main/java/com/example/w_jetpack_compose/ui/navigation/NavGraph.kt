package com.example.w_jetpack_compose.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.w_jetpack_compose.ui.notifications.NotificationsScreen
import com.example.w_jetpack_compose.ui.post.PostDetailScreen
import com.example.w_jetpack_compose.ui.post.PostScreen
import com.example.w_jetpack_compose.ui.profile.ProfileScreen
import com.example.w_jetpack_compose.ui.search.SearchScreen
import com.example.w_jetpack_compose.ui.settings.SettingsScreen
import kotlinx.coroutines.flow.collectLatest
import org.koin.compose.koinInject

@Composable
fun AppNavHost(
    navController: NavHostController,
    isDarkMode: Boolean,
    onThemeToggle: () -> Unit,
    navigator: Navigator = koinInject()
) {
    LaunchedEffect(Unit) {
        navigator.navigationActions.collectLatest { action ->
            when (action) {
                is NavigationAction.NavigateTo -> {
                    navController.navigate(action.route) {
                        launchSingleTop = action.singleTop
                        action.popUpTo?.let { popRoute ->
                            popUpTo(popRoute) { inclusive = action.inclusive }
                        }
                    }
                }
                is NavigationAction.NavigateAndClearStack -> {
                    navController.navigate(action.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
                is NavigationAction.NavigateBack -> {
                    navController.popBackStack()
                }
                is NavigationAction.NavigateBackTo -> {
                    navController.popBackStack(action.route, action.inclusive)
                }
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = Route.PostList
    ) {
        composable<Route.PostList> {
            PostScreen(
                isDarkMode = isDarkMode,
                onThemeToggle = onThemeToggle,
                onPostClick = { postId ->
                    navController.navigate(Route.PostDetail(postId))
                }
            )
        }
        
        composable<Route.PostDetail> { backStackEntry ->
            val detail: Route.PostDetail = backStackEntry.toRoute()
            PostDetailScreen(
                postId = detail.id,
                onBack = { navController.popBackStack() }
            )
        }

        composable<Route.Profile> {
            ProfileScreen(
                onBack = { navController.popBackStack() },
                onSettingsClick = { navController.navigate(Route.Settings) }
            )
        }

        composable<Route.Settings> {
            SettingsScreen(
                onBack = { navController.popBackStack() }
            )
        }

        composable<Route.Search> {
            SearchScreen(
                onPostClick = { postId ->
                    navController.navigate(Route.PostDetail(postId))
                }
            )
        }

        composable<Route.Notifications> {
            NotificationsScreen()
        }

        composable<Route.UserPosts> { backStackEntry ->
            val userPosts: Route.UserPosts = backStackEntry.toRoute()
            PostScreen(
                isDarkMode = isDarkMode,
                onThemeToggle = onThemeToggle,
                onPostClick = { postId ->
                    navController.navigate(Route.PostDetail(postId))
                }
            )
        }
    }
}
