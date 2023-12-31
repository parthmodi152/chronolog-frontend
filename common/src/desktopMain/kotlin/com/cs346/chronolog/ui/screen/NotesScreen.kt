package com.cs346.chronolog.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.LocalScrollbarStyle
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowState
import com.cs346.chronolog.ui.components.ChronologTwoPane
import com.cs346.chronolog.ui.components.UIState
import com.cs346.chronolog.ui.note.NoteDetailScreen
import com.cs346.chronolog.ui.note.NoteListScreen
import com.cs346.chronolog.ui.note.NotesSinglePaneContent
import com.cs346.chronolog.ui.utils.ChronologContentType
import kotlinx.coroutines.launch

@Composable
fun NotesScreen(
    modifier: Modifier = Modifier,
    windowState: WindowState,
    notesUIState: UIState,
    closeDetailScreen: () -> Unit,
    navigateToDetail: (Long, ChronologContentType) -> Unit,
) {
    val noteLazyListState = rememberLazyListState()
    val isTwoPane: Boolean = windowState.size.width > 850.dp
    val stateVertical = rememberScrollState()
    val scope = rememberCoroutineScope()
    AnimatedVisibility(
        visible = isTwoPane,
        enter = expandHorizontally(initialWidth = { it * 5 / 13 }),
        exit = shrinkHorizontally(targetWidth = { it * 5 / 13 }) + fadeOut()
    ) {
        ChronologTwoPane(
            modifier = modifier,
            first = {
                NoteListScreen(
                    notes = notesUIState.notes,
                    noteLazyListState = noteLazyListState,
                    navigateToDetail = { id, type ->
                        scope.launch {
                            stateVertical.animateScrollTo(0)
                        }
                        navigateToDetail(id, type)
                    },
                )
            },
            second = {

                NoteDetailScreen(
                    note = notesUIState.selectedNote ?: notesUIState.notes.first(),
                    isFullScreen = false,
                    stateVertical = stateVertical
                )
                VerticalScrollbar(
                    modifier = Modifier.align(Alignment.CenterEnd)
                        .padding(vertical = 10.dp)
                        .padding(top = 46.dp)
                        .fillMaxHeight(),
                    adapter = rememberScrollbarAdapter(stateVertical),
                    style = LocalScrollbarStyle.current.copy(
                        hoverColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        unhoverColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                )
            }
        )
    }

    AnimatedVisibility(
        visible = !isTwoPane,
        enter = expandHorizontally(initialWidth = { it * 5 / 13 }),
        exit = shrinkHorizontally(targetWidth = { it * 5 / 13 })
    ) {
        Box(modifier.fillMaxSize()) {
            VerticalScrollbar(
                modifier = Modifier.align(Alignment.CenterEnd)
                    .padding(vertical = 10.dp)
                    .padding(top = 46.dp)
                    .fillMaxHeight(),
                adapter = rememberScrollbarAdapter(stateVertical),
                style = LocalScrollbarStyle.current.copy(
                    hoverColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    unhoverColor = MaterialTheme.colorScheme.surfaceVariant
                )
            )
            NotesSinglePaneContent(
                modifier = modifier.fillMaxSize(),
                notesUIState = notesUIState,
                noteLazyListState = noteLazyListState,
                closeDetailScreen = closeDetailScreen,
                navigateToDetail = { id, type ->
                    scope.launch {
                        stateVertical.animateScrollTo(0)
                    }
                    navigateToDetail(id, type)
                },
                stateVertical = stateVertical
            )
        }
    }
}
