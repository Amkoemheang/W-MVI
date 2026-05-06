package com.example.w_jetpack_compose.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.w_jetpack_compose.ui.post.PostDetailScreen
import com.example.w_jetpack_compose.ui.post.PostScreen
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
                        action.popUpTo?.let {
                            popUpTo(it) { inclusive = action.inclusive }
                        }
                    }
                }
                NavigationAction.NavigateBack -> {
                    navController.popBackStack()
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
                onBack = {
                    navController.popBackStack() 
                }
            )
        }
    }
}
