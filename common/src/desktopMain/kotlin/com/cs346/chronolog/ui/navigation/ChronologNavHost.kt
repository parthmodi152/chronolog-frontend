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
import com.cs346.chronolog.ui.contact.ContactsUIState
import com.cs346.chronolog.ui.contact.ContactsViewModel
import com.cs346.chronolog.ui.edit.EditViewModel
import com.cs346.chronolog.ui.note.NotesUIState
import com.cs346.chronolog.ui.note.NotesViewModel
import com.cs346.chronolog.ui.screen.*
import com.cs346.chronolog.ui.tag.TagsUIState
import com.cs346.chronolog.ui.tag.TagsViewModel
import com.cs346.chronolog.ui.utils.ChronologContentType
import kotlinx.coroutines.CoroutineScope
import moe.tlaster.precompose.navigation.BackHandler
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.path
import moe.tlaster.precompose.navigation.transition.NavTransition

@Composable
fun ChronologNavHost(
    navigator: Navigator,
    windowState: WindowState,
    contentType: ChronologContentType,
    notesViewModel: NotesViewModel,
    contactsViewModel: ContactsViewModel,
    categoriesViewModel: CategoriesViewModel,
    tagsViewModel: TagsViewModel,
    editViewModel: EditViewModel,
    scope: CoroutineScope
) {
    val notesUIState by notesViewModel.uiState.collectAsState(null)
    val contactsUIState by contactsViewModel.uiState.collectAsState(null)
    val categoriesUIState by categoriesViewModel.uiState.collectAsState(null)
    val tagsUIState by tagsViewModel.uiState.collectAsState(null)
    val editUIState by editViewModel.uiState.collectAsState(null)
    val addNote = {
        // 防止多次点击
        if (editViewModel.noteSubject == "") {
            if (notesUIState?.selectedNote != null) {
                val route =
                    "${EditDestination.route}?${EditDestination.noteId}=${notesUIState!!.selectedNote!!.id}"
                navigator.navigate(route)
                editViewModel.editingNote = notesUIState!!.selectedNote
            } else {
                navigator.navigate(EditDestination.route)
            }
            editViewModel.onInit()
        }
    }
    NavHost(
        modifier = Modifier,
        navigator = navigator,
        initialRoute = ChronologRoute.NOTES,
        navTransition = NavTransition(
            createTransition = expandHorizontally(expandFrom = Alignment.Start),
            destroyTransition = shrinkHorizontally(shrinkTowards = Alignment.Start) + fadeOut(),
            resumeTransition = expandHorizontally(expandFrom = Alignment.Start),
            pauseTransition = shrinkHorizontally(shrinkTowards = Alignment.Start) + fadeOut(),
        )
    ) {
        scene(
            route = ChronologRoute.NOTES
        ) {
            NotesScreen(
                modifier = Modifier.padding(end = 3.dp),
                windowState = windowState,
                notesUIState = notesUIState ?: NotesUIState(error = "Error!"),
                addNote = addNote,
                navigateToDetail = { noteId, pane ->
                    notesViewModel.setSelectedNote(noteId, pane)
                },
                closeDetailScreen = {
                    notesViewModel.closeDetailScreen()
                }
            )
        }
        scene(
            route = ChronologRoute.CONTACTS
        ) {
            ContactsScreen(
                contactsUIState = contactsUIState ?: ContactsUIState(error = "Error!"),
                windowState = windowState,
                closeDetailScreen = {
                    contactsViewModel.closeDetailScreen()
                },
                navigateToDetail = { accountId, pane ->
                    contactsViewModel.setSelectedAccount(accountId, pane)
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
        scene(
            route = ChronologRoute.TAGS
        ) {
            TagsScreen(
                windowState = windowState,
                tagsUIState = tagsUIState ?: TagsUIState(error = "Error!"),
                addNote = addNote,
                closeDetailScreen = {
                    tagsViewModel.closeDetailScreen()
                },
                navigateToDetail = { noteId, pane ->
                    tagsViewModel.setSelectedNote(noteId, pane)
                },
            )
        }
        scene(
            route = EditDestination.routeWithArg
        ) { backStackEntry ->
            editViewModel.noteId = backStackEntry.path<Long>(EditDestination.noteId) ?: -1
            BackHandler {
                editViewModel.onBackPress()
            }
            EditScreen(
                editViewModel = editViewModel,
                modifier = Modifier.padding(end = 3.dp),
                windowState = windowState,
                title = editViewModel.noteSubject,
                text = editViewModel.noteBody,
                categories = editUIState?.categories?.map { it.name } ?: emptyList(),
                tags = editUIState?.tags?.map { it.name } ?: emptyList(),
                isEditModeEnabled = editViewModel.isEditModeEnabled,
                isOpenNoteInfoDialog = editViewModel.isNoteInfoDialogOpen,
                isOpenSaveDialog = editViewModel.isSaveDialogOpen,
                titleEditorFocus = editViewModel.titleEditorFocus,
                onBodyValueChange = { editViewModel.onBodyValueChange(it) },
                onSubjectValueChange = { editViewModel.onSubjectValueChange(it) },
                onConfirm = { editViewModel.onConfirm() },
                onConfirmClose = {
                    editViewModel.clearAll()
                    navigator.popBackStack()
                },
                onDismissRequest = {
                    if (editViewModel.noteSubject == "") {
                        editViewModel.clearAll()
                        navigator.popBackStack()
                    } else {
                        editViewModel.isNoteInfoDialogOpen = false
                    }
                },
                onDismissSaveRequest = { editViewModel.isSaveDialogOpen = false },
                onModeChanged = { editViewModel.modeChange() },
                onClickedTextButton = { char ->
                    //TODO input char
                },
                onBackPressed = { editViewModel.onBackPress() },
                onClickTitle = {
                    editViewModel.isNoteInfoDialogOpen = true
                }
            )
        }
    }
}
