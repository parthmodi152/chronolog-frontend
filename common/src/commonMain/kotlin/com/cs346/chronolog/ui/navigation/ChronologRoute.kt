package com.cs346.chronolog.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Article
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.ui.graphics.vector.ImageVector

object ChronologRoute {
    const val NOTES = "Notes"
    const val CONTACTS = "Contacts"
    const val CATEGORIES = "Categories"
    const val TAGS = "Tags"
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
        route = ChronologRoute.CONTACTS,
        selectedIcon = Icons.Default.Groups,
        unselectedIcon = Icons.Default.Groups,
        iconText = "Contacts"
    ),
    ChronologTopLevelDestination(
        route = ChronologRoute.CATEGORIES,
        selectedIcon = Icons.Default.Category,
        unselectedIcon = Icons.Default.Category,
        iconText = "Categories"
    ),
    ChronologTopLevelDestination(
        route = ChronologRoute.TAGS,
        selectedIcon = Icons.Default.LocalOffer,
        unselectedIcon = Icons.Default.LocalOffer,
        iconText = "Tags"
    ),
)
