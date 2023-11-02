package com.cs346.chronolog

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowState
import com.cs346.chronolog.ui.category.CategoriesViewModel
import com.cs346.chronolog.ui.navigation.*
import com.cs346.chronolog.ui.note.NotesViewModel
import com.cs346.chronolog.ui.utils.ChronologContentType
import com.cs346.chronolog.ui.utils.ChronologNavigationContentPosition
import com.cs346.chronolog.ui.utils.ChronologNavigationType
import kotlinx.coroutines.CoroutineScope
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.rememberNavigator

@Composable
fun ChronologDesktopApp(
    windowState: WindowState,
    notesViewModel: NotesViewModel,
    categoriesViewModel: CategoriesViewModel,
) {
    val scope = rememberCoroutineScope()

    var contentType: ChronologContentType by mutableStateOf(ChronologContentType.SINGLE_PANE)

    contentType = if (windowState.size.width < 800.dp) {
        ChronologContentType.SINGLE_PANE
    } else {
        ChronologContentType.DUAL_PANE
    }

    val navigator = rememberNavigator()
    val navigationActions = remember(navigator) { NavigationActions(navigator) }
    val backStackEntry by navigator.currentEntry.collectAsState(null)
    val selectedDestination = backStackEntry?.route?.route ?: ChronologRoute.NOTES

    ChronologDesktopAppContent(
        navigator = navigator,
        windowState = windowState,
        navigationType = ChronologNavigationType.NAVIGATION_RAIL,
        contentType = contentType,
        selectedDestination = selectedDestination,
        navigateToTopLevelDestination = navigationActions::navigateTo,
        notesViewModel = notesViewModel,
        categoriesViewModel = categoriesViewModel,
        scope = scope
    )
}

@Composable
fun ChronologDesktopAppContent(
    modifier: Modifier = Modifier,
    windowState: WindowState,
    navigator: Navigator,
    navigationType: ChronologNavigationType,
    contentType: ChronologContentType,
    notesViewModel: NotesViewModel,
    categoriesViewModel: CategoriesViewModel,
    selectedDestination: String = ChronologRoute.NOTES,
    navigationContentPosition: ChronologNavigationContentPosition = ChronologNavigationContentPosition.CENTER,
    navigateToTopLevelDestination: (ChronologTopLevelDestination) -> Unit,
    scope: CoroutineScope,
) {
    Row(modifier = modifier.fillMaxSize()) {
        AnimatedVisibility(visible = navigationType == ChronologNavigationType.NAVIGATION_RAIL) {
            ChronologNavigationRail(
                selectedDestination = selectedDestination,
                navigationContentPosition = navigationContentPosition,
                navigateToTopLevelDestination = navigateToTopLevelDestination,
                onDrawerClicked = { },
                menuVisibility = false
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.inverseOnSurface)
        ) {
            ChronologNavHost(
                navigator = navigator,
                windowState = windowState,
                notesViewModel = notesViewModel,
                categoriesViewModel = categoriesViewModel,
            )
        }
    }
}
