package com.example.w_jetpack_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CloudDone
import androidx.compose.material.icons.filled.CloudOff
import androidx.compose.material.icons.filled.WifiOff
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.w_jetpack_compose.core.connectivity.ConnectivityObserver
import com.example.w_jetpack_compose.ui.navigation.AppNavHost
import com.example.w_jetpack_compose.ui.navigation.BottomNavigationItem
import com.example.w_jetpack_compose.ui.theme.WJetPackComposeTheme
import kotlinx.coroutines.delay
import org.koin.compose.koinInject

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val systemInDarkTheme = isSystemInDarkTheme()
            var isDarkMode by remember { mutableStateOf(systemInDarkTheme) }

            WJetPackComposeTheme(darkTheme = isDarkMode) {
                ConnectivityWrapper {
                    MainScreen(
                        isDarkMode = isDarkMode,
                        onThemeToggle = { isDarkMode = !isDarkMode }
                    )
                }
            }
        }
    }
}

@Composable
fun MainScreen(
    isDarkMode: Boolean,
    onThemeToggle: () -> Unit
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val bottomItems = listOf(
        BottomNavigationItem.Home,
        BottomNavigationItem.Profile,
        BottomNavigationItem.Search,
        BottomNavigationItem.Notifications
    )

    // Check if current route is a bottom navigation destination
    val showBottomBar = bottomItems.any { item ->
        currentDestination?.hasRoute(item.route::class) == true
    }

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                NavigationBar {
                    bottomItems.forEach { item ->
                        val isSelected = currentDestination?.hasRoute(item.route::class) == true
                        NavigationBarItem(
                            selected = isSelected,
                            label = { Text(item.label) },
                            icon = {
                                Icon(
                                    imageVector = item.icon,
                                    contentDescription = item.label
                                )
                            },
                            onClick = {
                                navController.navigate(item.route) {
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
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding).background(color = Blue)) {
            AppNavHost(
                navController = navController,
                isDarkMode = isDarkMode,
                onThemeToggle = onThemeToggle
            )
        }
    }
}

@Composable
fun ConnectivityWrapper(
    connectivityObserver: ConnectivityObserver = koinInject(),
    content: @Composable () -> Unit
) {
    val status by connectivityObserver.observe().collectAsState(
        initial = ConnectivityObserver.Status.Available
    )

    var showSuccessBanner by remember { mutableStateOf(false) }
    var previousStatus by remember { mutableStateOf(ConnectivityObserver.Status.Available) }

    LaunchedEffect(status) {
        if (previousStatus != ConnectivityObserver.Status.Available && status == ConnectivityObserver.Status.Available) {
            showSuccessBanner = true
            delay(3000)
            showSuccessBanner = false
        }
        previousStatus = status
    }

    Box {
        content()
        
        val isOffline = status != ConnectivityObserver.Status.Available
        val isVisible = isOffline || showSuccessBanner

        val colorStart by animateColorAsState(
            targetValue = if (isOffline) Color(0xFFF44336) else Color(0xFF4CAF50),
            animationSpec = tween(durationMillis = 600),
            label = "gradientStart"
        )
        val colorEnd by animateColorAsState(
            targetValue = if (isOffline) Color(0xFFB71C1C) else Color(0xFF1B5E20),
            animationSpec = tween(durationMillis = 600),
            label = "gradientEnd"
        )

        val gradientBrush = Brush.verticalGradient(
            colors = listOf(colorStart, colorEnd)
        )

        val icon = when (status) {
            ConnectivityObserver.Status.Available -> Icons.Default.CloudDone
            ConnectivityObserver.Status.Losing -> Icons.Default.CloudOff
            else -> Icons.Default.WifiOff
        }

        val message = when (status) {
            ConnectivityObserver.Status.Available -> "Back Online"
            ConnectivityObserver.Status.Unavailable -> "No Internet Connection"
            ConnectivityObserver.Status.Losing -> "Connection is unstable..."
            ConnectivityObserver.Status.Lost -> "Internet connection lost"
        }

        AnimatedVisibility(
            visible = isVisible,
            enter = fadeIn() + expandVertically(),
            exit = fadeOut() + shrinkVertically(),
            modifier = Modifier
                .align(Alignment.TopCenter)
                .statusBarsPadding()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Surface(
                color = Color.Transparent,
                contentColor = Color.White,
                shape = RoundedCornerShape(24.dp),
                shadowElevation = 12.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(gradientBrush, RoundedCornerShape(24.dp))
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = message,
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontWeight = FontWeight.ExtraBold,
                            letterSpacing = 0.5.sp
                        )
                    )
                }
            }
        }
    }
}
