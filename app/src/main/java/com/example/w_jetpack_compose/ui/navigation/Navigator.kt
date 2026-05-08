package com.example.w_jetpack_compose.ui.navigation

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

interface Navigator {
    val navigationActions: kotlinx.coroutines.flow.SharedFlow<NavigationAction>
    
    fun navigateTo(route: Route, popUpTo: Route? = null, inclusive: Boolean = false, singleTop: Boolean = false)
    fun navigateAndClearStack(route: Route)
    fun navigateBack()
    fun navigateBackTo(route: Route, inclusive: Boolean = false)
}

sealed interface NavigationAction {
    data class NavigateTo(
        val route: Route,
        val popUpTo: Route? = null,
        val inclusive: Boolean = false,
        val singleTop: Boolean = false
    ) : NavigationAction

    data class NavigateAndClearStack(val route: Route) : NavigationAction
    data object NavigateBack : NavigationAction
    data class NavigateBackTo(val route: Route, val inclusive: Boolean) : NavigationAction
}

class NavigatorImpl : Navigator {
    private val _navigationActions = MutableSharedFlow<NavigationAction>(extraBufferCapacity = 1)
    override val navigationActions = _navigationActions.asSharedFlow()

    override fun navigateTo(route: Route, popUpTo: Route?, inclusive: Boolean, singleTop: Boolean) {
        _navigationActions.tryEmit(NavigationAction.NavigateTo(route, popUpTo, inclusive, singleTop))
    }

    override fun navigateAndClearStack(route: Route) {
        _navigationActions.tryEmit(NavigationAction.NavigateAndClearStack(route))
    }

    override fun navigateBack() {
        _navigationActions.tryEmit(NavigationAction.NavigateBack)
    }

    override fun navigateBackTo(route: Route, inclusive: Boolean) {
        _navigationActions.tryEmit(NavigationAction.NavigateBackTo(route, inclusive))
    }
}
