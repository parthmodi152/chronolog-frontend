package com.cs346.chronolog.ui.navigation

import moe.tlaster.precompose.navigation.NavOptions
import moe.tlaster.precompose.navigation.Navigator

object EditDestination : ChronologRouteDestination {
    override val route: String = "Edit"
    const val noteId = "{noteId}?"
    val routeWithArg = "$route/$noteId"
}

class NavigationActions(private val navigator: Navigator) {

    fun navigateTo(destination: ChronologTopLevelDestination) {
        navigator.navigate(
            destination.route,
            NavOptions(
                launchSingleTop = true
            )
        )
    }
}
