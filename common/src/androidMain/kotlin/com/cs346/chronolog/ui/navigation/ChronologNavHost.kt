package com.cs346.chronolog.ui.navigation

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.window.layout.DisplayFeature
import com.cs346.chronolog.ui.category.CategoriesViewModel
import com.cs346.chronolog.ui.note.NotesViewModel
import com.cs346.chronolog.ui.screen.*
import com.cs346.chronolog.ui.utils.ChronologContentType
import com.cs346.chronolog.ui.utils.ChronologNavigationType
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun ChronologNavHost(
    navController: NavHostController,
    contentType: ChronologContentType,
    displayFeatures: List<DisplayFeature>,
    notesViewModel: NotesViewModel,
    categoriesViewModel: CategoriesViewModel,
    navigationType: ChronologNavigationType,
    modifier: Modifier = Modifier,
    scope: CoroutineScope
) {
    val notesUIState by notesViewModel.uiState.collectAsStateWithLifecycle()
    val categoriesUIState by categoriesViewModel.uiState.collectAsStateWithLifecycle()
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = ChronologRoute.NOTES
    ) {
        composable(ChronologRoute.NOTES) {
            NoteScreen(
                contentType = contentType,
                notesUIState = notesUIState,
                navigationType = navigationType,
                displayFeatures = displayFeatures,
                closeDetailScreen = {
                    notesViewModel.closeDetailScreen()
                },
                navigateToDetail = { noteId, pane ->
                    notesViewModel.setSelectedNote(noteId, pane)
                },
            )
        }
        composable(ChronologRoute.CATEGORIES) {
            CategoriesScreen(
                categoriesUIState = categoriesUIState,
                contentType = contentType,
                closeDetailScreen = { categoriesViewModel.closeDetailScreen() },
                displayFeatures = displayFeatures,
                navigateToDetail = { categoryId, pane ->
                    categoriesViewModel.setSelectedCategory(categoryId, pane)
                }
            )
        }
    }
}
