package com.cs346.chronolog.ui.note

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.cs346.chronolog.ui.components.UIState
import com.cs346.chronolog.ui.utils.ChronologContentType
import moe.tlaster.precompose.navigation.BackHandler

@Composable
fun NotesSinglePaneContent(
    modifier: Modifier = Modifier,
    notesUIState: UIState,
    noteLazyListState: LazyListState,
    closeDetailScreen: () -> Unit,
    navigateToDetail: (Long, ChronologContentType) -> Unit,
    stateVertical: ScrollState = rememberScrollState()
) {
    val detailVisibility: Boolean =
        notesUIState.selectedNote != null && notesUIState.isDetailOnlyOpen

    AnimatedVisibility(
        visible = !detailVisibility,
        enter = slideInHorizontally(initialOffsetX = { -it }),
        exit = slideOutHorizontally(targetOffsetX = { -it }) + fadeOut()
    ) {
        NoteListScreen(
            notes = notesUIState.notes,
            noteLazyListState = noteLazyListState,
            modifier = modifier,
            navigateToDetail = navigateToDetail,
        )
    }
    AnimatedVisibility(
        visible = detailVisibility,
        enter = slideInHorizontally(initialOffsetX = { it }),
        exit = slideOutHorizontally(targetOffsetX = { it }) + fadeOut()
    ) {
        BackHandler {
            closeDetailScreen()
        }
        notesUIState.selectedNote?.let {
            NoteDetailScreen(
                note = it,
                onBackPressed = closeDetailScreen,
                stateVertical = stateVertical
            )
        }
    }
}
