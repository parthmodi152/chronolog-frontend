package com.cs346.chronolog.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Article
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.ui.graphics.vector.ImageVector

object ChronologRoute {
    const val NOTES = "Notes"
    const val CATEGORIES = "Categories"
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
        selectedIcon = Icons.Default.Article,
        unselectedIcon = Icons.Default.Article,
        iconText = "Notes"
    ),
    ChronologTopLevelDestination(
        route = ChronologRoute.CATEGORIES,
        selectedIcon = Icons.Default.Category,
        unselectedIcon = Icons.Default.Category,
        iconText = "Categories"
    ),
)
