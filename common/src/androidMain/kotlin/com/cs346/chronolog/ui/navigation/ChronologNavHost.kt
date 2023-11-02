package com.cs346.chronolog.ui.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.window.layout.DisplayFeature
import com.cs346.chronolog.ui.category.CategoriesViewModel
import com.cs346.chronolog.ui.contact.ContactsViewModel
import com.cs346.chronolog.ui.edit.EditViewModel
import com.cs346.chronolog.ui.note.NotesViewModel
import com.cs346.chronolog.ui.screen.*
import com.cs346.chronolog.ui.tag.TagsViewModel
import com.cs346.chronolog.ui.utils.InputKeyWordsUtil
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
    contactsViewModel: ContactsViewModel,
    categoriesViewModel: CategoriesViewModel,
    tagsViewModel: TagsViewModel,
    editViewModel: EditViewModel,
    navigationType: ChronologNavigationType,
    modifier: Modifier = Modifier,
    scope: CoroutineScope
) {
    val notesUIState by notesViewModel.uiState.collectAsStateWithLifecycle()
    val contactsUIState by contactsViewModel.uiState.collectAsStateWithLifecycle()
    val categoriesUIState by categoriesViewModel.uiState.collectAsStateWithLifecycle()
    val tagsUIState by tagsViewModel.uiState.collectAsStateWithLifecycle()
    val editUIState by editViewModel.uiState.collectAsStateWithLifecycle()
    val addNote = {
        if (notesUIState.selectedNote != null) {
            val route =
                "${EditDestination.route}?${EditDestination.noteId}=${notesUIState.selectedNote!!.id}"
            navController.navigate(route)
            editViewModel.editingNote = notesUIState.selectedNote
        } else {
            navController.navigate(EditDestination.route)
        }
        editViewModel.onInit()
    }
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
                addNote = addNote,
                closeDetailScreen = {
                    notesViewModel.closeDetailScreen()
                },
                navigateToDetail = { noteId, pane ->
                    notesViewModel.setSelectedNote(noteId, pane)
                },
            )
        }
        composable(ChronologRoute.CONTACTS) {
            ContactsScreen(
                contactsUIState = contactsUIState,
                contentType = contentType,
                closeDetailScreen = {
                    contactsViewModel.closeDetailScreen()
                },
                displayFeatures = displayFeatures,
                navigateToDetail = { accountId, pane ->
                    contactsViewModel.setSelectedAccount(accountId, pane)
                }
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
        composable(ChronologRoute.TAGS) {
            TagsScreen(
                contentType = contentType,
                tagsUIState = tagsUIState,
                navigationType = navigationType,
                displayFeatures = displayFeatures,
                addNote = addNote,
                closeDetailScreen = {
                    tagsViewModel.closeDetailScreen()
                },
                navigateToDetail = { noteId, pane ->
                    tagsViewModel.setSelectedNote(noteId, pane)
                },
            )
        }
        composable(
            route = EditDestination.routeWithArg,
            arguments = EditDestination.arguments
        ) { navBackStackEntry ->
            editViewModel.noteId =
                navBackStackEntry.arguments?.getLong(EditDestination.noteId) ?: -1
            BackHandler(enabled = true) {
                editViewModel.onBackPress()
            }

            EditScreen(
                editViewModel = editViewModel,
                modifier = Modifier.imePadding().systemBarsPadding(),
                contentType = contentType,
                displayFeatures = displayFeatures,
                title = editViewModel.noteSubject,
                text = editViewModel.noteBody,
                categories = editUIState.categories.map { it.name },
                tags = editUIState.tags.map { it.name },
                isEditModeEnabled = editViewModel.isEditModeEnabled,
                isOpenNoteInfoDialog = editViewModel.isNoteInfoDialogOpen,
                isOpenSaveDialog = editViewModel.isSaveDialogOpen,
                titleEditorFocus = editViewModel.titleEditorFocus,
                onBodyValueChange = { editViewModel.onBodyValueChange(it) },
                onSubjectValueChange = { editViewModel.onSubjectValueChange(it) },
                onConfirm = { editViewModel.onConfirm() },
                onConfirmClose = {
                    editViewModel.clearAll()
                    navController.popBackStack()
                },
                onDismissRequest = {
                    if (editViewModel.noteSubject == "") {
                        editViewModel.clearAll()
                        navController.popBackStack()
                    } else {
                        editViewModel.isNoteInfoDialogOpen = false
                    }
                },
                onDismissSaveRequest = { editViewModel.isSaveDialogOpen = false },
                onModeChanged = { editViewModel.modeChange() },
                onClickedTextButton = { char ->
                    InputKeyWordsUtil.keyCodeMap[char]?.let {
                        InputKeyWordsUtil.input(
                            scope = scope,
                            it
                        )
                    }
                },
                onBackPressed = { editViewModel.onBackPress() },
                onClickTitle = {
                    editViewModel.isNoteInfoDialogOpen = true
                }
            )
        }
    }
}
