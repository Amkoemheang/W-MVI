package com.example.w_jetpack_compose.ui.notifications

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationsScreen() {
    val notifications = listOf(
        "New post from John Doe",
        "Your post received a comment",
        "Welcome to the app!",
        "Update available for W-JetPack-Compose",
        "Someone liked your profile"
    )

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Notifications") })
        }
    ) { innerPadding ->
        if (notifications.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Text("No notifications yet")
            }
        } else {
            LazyColumn(modifier = Modifier.padding(innerPadding)) {
                items(notifications) { notification ->
                    ListItem(
                        headlineContent = { Text(notification) },
                        supportingContent = { Text("Just now") }
                    )
                    HorizontalDivider()
                }
            }
        }
    }
}
