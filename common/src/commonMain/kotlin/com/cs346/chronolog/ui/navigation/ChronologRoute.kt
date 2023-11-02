package com.cs346.chronolog.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ListAlt
import androidx.compose.material.icons.filled.Folder
import androidx.compose.ui.graphics.vector.ImageVector

object ChronologRoute {
    const val NOTES = "Notes"
    const val CATEGORIES = "Categories"
    const val LOGIN = "LOGIN"
}

interface ChronologRouteDestination {
    val route: String
}

data class ChronologTopLevelDestination(
    override val route: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val iconText: String
) : ChronologRouteDestination

val TOP_LEVEL_DESTINATIONS = listOf(
    ChronologTopLevelDestination(
        route = ChronologRoute.NOTES,
        selectedIcon = Icons.Default.ListAlt,
        unselectedIcon = Icons.Default.ListAlt,
        iconText = "Notes"
    ),
    ChronologTopLevelDestination(
        route = ChronologRoute.CATEGORIES,
        selectedIcon = Icons.Default.Folder,
        unselectedIcon = Icons.Default.Folder,
        iconText = "Categories"
    ),
)
