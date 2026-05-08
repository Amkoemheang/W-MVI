package com.example.w_jetpack_compose.ui.navigation

import kotlinx.serialization.Serializable

sealed interface Route {
    @Serializable
    data object PostList : Route

    @Serializable
    data class PostDetail(val id: Int) : Route

    @Serializable
    data object Profile : Route

    @Serializable
    data object Settings : Route

    @Serializable
    data class UserPosts(val userId: Int) : Route

    @Serializable
    data object Search : Route

    @Serializable
    data object Notifications : Route
}
