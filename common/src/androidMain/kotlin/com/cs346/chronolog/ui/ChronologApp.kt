package com.cs346.chronolog.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.window.layout.DisplayFeature
import androidx.window.layout.FoldingFeature
import com.cs346.chronolog.ui.category.CategoriesViewModel
import com.cs346.chronolog.ui.navigation.*
import com.cs346.chronolog.ui.note.NotesViewModel
import com.cs346.chronolog.ui.utils.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun ChronologApp(
    windowSize: WindowSizeClass,
    displayFeatures: List<DisplayFeature>,
    notesViewModel: NotesViewModel,
    categoriesViewModel: CategoriesViewModel,
) {
    val navigationType: ChronologNavigationType
    val contentType: ChronologContentType
    val foldingFeature = displayFeatures.filterIsInstance<FoldingFeature>().firstOrNull()
    val foldingDevicePosture = when {
        isBookPosture(foldingFeature) ->
            DevicePosture.BookPosture(foldingFeature.bounds)

        isSeparating(foldingFeature) ->
            DevicePosture.Separating(foldingFeature.bounds, foldingFeature.orientation)

        else -> DevicePosture.NormalPosture
    }

    when (windowSize.widthSizeClass) {
        WindowWidthSizeClass.Compact -> {
            navigationType = ChronologNavigationType.BOTTOM_NAVIGATION
            contentType = ChronologContentType.SINGLE_PANE
        }

        WindowWidthSizeClass.Medium -> {
            navigationType = ChronologNavigationType.NAVIGATION_RAIL
            contentType = if (foldingDevicePosture != DevicePosture.NormalPosture) {
                ChronologContentType.DUAL_PANE
            } else {
                ChronologContentType.SINGLE_PANE
            }
        }

        WindowWidthSizeClass.Expanded -> {
            navigationType = if (foldingDevicePosture is DevicePosture.BookPosture) {
                ChronologNavigationType.NAVIGATION_RAIL
            } else {
                ChronologNavigationType.PERMANENT_NAVIGATION_DRAWER
            }
            contentType = ChronologContentType.DUAL_PANE
        }

        else -> {
            navigationType = ChronologNavigationType.BOTTOM_NAVIGATION
            contentType = ChronologContentType.SINGLE_PANE
        }
    }

    val navigationContentPosition = when (windowSize.heightSizeClass) {
        WindowHeightSizeClass.Compact -> {
            ChronologNavigationContentPosition.TOP
        }

        WindowHeightSizeClass.Medium,
        WindowHeightSizeClass.Expanded -> {
            ChronologNavigationContentPosition.CENTER
        }

        else -> {
            ChronologNavigationContentPosition.TOP
        }
    }
    Column(
        modifier = Modifier.fillMaxSize().background(
            color = MaterialTheme.colorScheme.onSurface.copy(0.02F)
        ).statusBarsPadding()
    )
    {
        ChronologNavigationWrapper(
            navigationType = navigationType,
            contentType = contentType,
            displayFeatures = displayFeatures,
            navigationContentPosition = navigationContentPosition,
            notesViewModel = notesViewModel,
            categoriesViewModel = categoriesViewModel,
        )
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChronologNavigationWrapper(
    navigationType: ChronologNavigationType,
    contentType: ChronologContentType,
    displayFeatures: List<DisplayFeature>,
    navigationContentPosition: ChronologNavigationContentPosition,
    notesViewModel: NotesViewModel,
    categoriesViewModel: CategoriesViewModel,
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val navController = rememberNavController()
    val navigationActions = remember(navController) {
        NavigationActions(navController)
    }

    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val selectedDestination = navBackStackEntry?.destination?.route ?: ChronologRoute.NOTES
    val chronologBottomVisible = navigationType == ChronologNavigationType.BOTTOM_NAVIGATION
    val addNote = { }
    if (navigationType == ChronologNavigationType.PERMANENT_NAVIGATION_DRAWER) {
        PermanentNavigationDrawer(drawerContent = {
            PermanentNavigationDrawerContent(
                selectedDestination = selectedDestination,
                navigationContentPosition = navigationContentPosition,
                navigateToTopLevelDestination = navigationActions::navigateTo,
                addNote = addNote,
            )
        }) {
            ChronologAppContent(
                navigationType = navigationType,
                navController = navController,
                selectedDestination = selectedDestination,
                contentType = contentType,
                displayFeatures = displayFeatures,
                navigationContentPosition = navigationContentPosition,
                notesViewModel = notesViewModel,
                categoriesViewModel = categoriesViewModel,
                navigateToTopLevelDestination = navigationActions::navigateTo,
                addNote = addNote,
                scope = scope
            )
        }
    } else {
        ModalNavigationDrawer(
            drawerContent = {
                ModalNavigationDrawerContent(
                    selectedDestination = selectedDestination,
                    navigationContentPosition = navigationContentPosition,
                    navigateToTopLevelDestination = navigationActions::navigateTo,
                    onDrawerClicked = {
                        scope.launch {
                            drawerState.close()
                        }
                    },
                    addNote = addNote,
                )
            },
            drawerState = drawerState
        ) {
            ChronologAppContent(
                navigationType = navigationType,
                navController = navController,
                selectedDestination = selectedDestination,
                contentType = contentType,
                notesViewModel = notesViewModel,
                categoriesViewModel = categoriesViewModel,
                displayFeatures = displayFeatures,
                navigationContentPosition = navigationContentPosition,
                navigateToTopLevelDestination = navigationActions::navigateTo,
                addNote = addNote,
                chronologBottomVisible = chronologBottomVisible,
                scope = scope
            ) {
                scope.launch {
                    drawerState.open()
                }
            }
        }
    }
}

@Composable
fun ChronologAppContent(
    modifier: Modifier = Modifier,
    navigationType: ChronologNavigationType,
    navController: NavHostController,
    selectedDestination: String,
    contentType: ChronologContentType,
    displayFeatures: List<DisplayFeature>,
    navigationContentPosition: ChronologNavigationContentPosition,
    notesViewModel: NotesViewModel,
    categoriesViewModel: CategoriesViewModel,
    navigateToTopLevelDestination: (ChronologTopLevelDestination) -> Unit,
    chronologBottomVisible: Boolean = false,
    addNote: () -> Unit,
    scope: CoroutineScope,
    onDrawerClicked: () -> Unit = {},
) {
    Row(modifier = modifier.fillMaxSize()) {
        AnimatedVisibility(visible = navigationType == ChronologNavigationType.NAVIGATION_RAIL) {
            ChronologNavigationRail(
                selectedDestination = selectedDestination,
                navigationContentPosition = navigationContentPosition,
                navigateToTopLevelDestination = navigateToTopLevelDestination,
                onDrawerClicked = onDrawerClicked,
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.inverseOnSurface)
        ) {
            ChronologNavHost(
                navController = navController,
                contentType = contentType,
                displayFeatures = displayFeatures,
                notesViewModel = notesViewModel,
                categoriesViewModel = categoriesViewModel,
                navigationType = navigationType,
                modifier = Modifier.weight(1f),
                scope = scope
            )
            AnimatedVisibility(visible = chronologBottomVisible) {
                ChronologBottomNavigationBar(
                    selectedDestination = selectedDestination,
                    navigateToTopLevelDestination = navigateToTopLevelDestination
                )
            }
        }
    }
}
