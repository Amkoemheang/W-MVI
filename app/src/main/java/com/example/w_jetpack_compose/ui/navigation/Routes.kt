package com.example.w_jetpack_compose.ui.navigation

import kotlinx.serialization.Serializable

sealed interface Route {
    @Serializable
    data object PostList : Route

    @Serializable
    data class PostDetail(val id: Int) : Route
}
