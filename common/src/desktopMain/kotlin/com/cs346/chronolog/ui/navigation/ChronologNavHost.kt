package com.cs346.chronolog.ui.navigation

import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowState
import com.cs346.chronolog.ui.category.CategoriesUIState
import com.cs346.chronolog.ui.category.CategoriesViewModel
import com.cs346.chronolog.ui.note.NotesUIState
import com.cs346.chronolog.ui.note.NotesViewModel
import com.cs346.chronolog.ui.login.LoginViewModel
import com.cs346.chronolog.ui.screen.*
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.NavOptions
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.transition.NavTransition

@Composable
fun ChronologNavHost(
    navigator: Navigator,
    windowState: WindowState,
    notesViewModel: NotesViewModel,
    loginViewModel: LoginViewModel,
    categoriesViewModel: CategoriesViewModel,
) {
    val notesUIState by notesViewModel.uiState.collectAsState(null)
    val categoriesUIState by categoriesViewModel.uiState.collectAsState(null)
    NavHost(
        modifier = Modifier,
        navigator = navigator,
        initialRoute = ChronologRoute.LOGIN, // change initialRoute to LOGIN
        navTransition = NavTransition(
            createTransition = expandHorizontally(expandFrom = Alignment.Start),
            destroyTransition = shrinkHorizontally(shrinkTowards = Alignment.Start) + fadeOut(),
            resumeTransition = expandHorizontally(expandFrom = Alignment.Start),
            pauseTransition = shrinkHorizontally(shrinkTowards = Alignment.Start) + fadeOut(),
        )
    ) {
        scene(
            route = ChronologRoute.LOGIN
        ) {
            LoginScreen(
                viewModel = loginViewModel,
                onLoginSuccess = {
                    navigator.navigate(
                        ChronologRoute.NOTES,
                        NavOptions(
                            launchSingleTop = true
                        )
                    )
                },
                onSignupSuccess = {
                    navigator.navigate(
                        ChronologRoute.LOGIN,
                        NavOptions(
                            launchSingleTop = true
                        )
                    )
                }
            )
        }
        scene(
            route = ChronologRoute.NOTES
        ) {
            NotesScreen(
                modifier = Modifier.padding(end = 3.dp),
                windowState = windowState,
                notesUIState = notesUIState ?: NotesUIState(error = "Error!"),
                navigateToDetail = { noteId, pane ->
                    notesViewModel.setSelectedNote(noteId, pane)
                },
                closeDetailScreen = {
                    notesViewModel.closeDetailScreen()
                }
            )
        }
        scene(
            route = ChronologRoute.CATEGORIES
        ) {
            CategoriesScreen(
                categoriesUIState = categoriesUIState ?: CategoriesUIState(error = "Error!"),
                windowState = windowState,
                closeDetailScreen = { categoriesViewModel.closeDetailScreen() },
                navigateToDetail = { categoryId, pane ->
                    categoriesViewModel.setSelectedCategory(categoryId, pane)
                }
            )
        }
    }
}
