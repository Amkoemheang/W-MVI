package com.example.w_jetpack_compose.ui.navigation

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

interface Navigator {
    val navigationActions: kotlinx.coroutines.flow.SharedFlow<NavigationAction>
    fun navigateTo(route: Route, popUpTo: Route? = null, inclusive: Boolean = false)
    fun navigateBack()
}

sealed interface NavigationAction {
    data class NavigateTo(val route: Route, val popUpTo: Route? = null, val inclusive: Boolean = false) : NavigationAction
    data object NavigateBack : NavigationAction
}

class NavigatorImpl : Navigator {
    private val _navigationActions = MutableSharedFlow<NavigationAction>(extraBufferCapacity = 1)
    override val navigationActions = _navigationActions.asSharedFlow()

    override fun navigateTo(route: Route, popUpTo: Route?, inclusive: Boolean) {
        _navigationActions.tryEmit(NavigationAction.NavigateTo(route, popUpTo, inclusive))
    }

    override fun navigateBack() {
        _navigationActions.tryEmit(NavigationAction.NavigateBack)
    }
}
