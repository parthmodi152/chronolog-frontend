package com.cs346.chronolog.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.window.layout.DisplayFeature
import com.google.accompanist.adaptive.HorizontalTwoPaneStrategy
import com.google.accompanist.adaptive.TwoPane
import com.cs346.chronolog.ui.components.UIState
import com.cs346.chronolog.ui.note.NoteDetailScreen
import com.cs346.chronolog.ui.note.NoteListScreen
import com.cs346.chronolog.ui.note.NotesSinglePaneContent
import com.cs346.chronolog.ui.utils.ChronologContentType
import com.cs346.chronolog.ui.utils.ChronologNavigationType
import com.cs346.chronolog.R
import kotlinx.coroutines.launch

@Composable
fun NoteScreen(
    contentType: ChronologContentType,
    notesUIState: UIState,
    navigationType: ChronologNavigationType,
    displayFeatures: List<DisplayFeature>,
    addNote: () -> Unit,
    closeDetailScreen: () -> Unit,
    navigateToDetail: (Long, ChronologContentType) -> Unit,
    modifier: Modifier = Modifier,
    tagsGrid: @Composable () -> Unit = {},
) {
    LaunchedEffect(key1 = contentType) {
        if ((contentType == ChronologContentType.SINGLE_PANE) && !notesUIState.isDetailOnlyOpen) {
            closeDetailScreen()
        }
    }
    val stateVertical = rememberScrollState()
    val scope = rememberCoroutineScope()
    val noteLazyListState = rememberLazyListState()

    if (contentType == ChronologContentType.DUAL_PANE) {
        TwoPane(
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
                    tagsGrid = tagsGrid
                )
            },
            second = {
                NoteDetailScreen(
                    note = notesUIState.selectedNote ?: notesUIState.notes.first(),
                    isFullScreen = false,
                    addNote = addNote,
                    stateVertical = stateVertical
                )
            },
            strategy = HorizontalTwoPaneStrategy(splitFraction = 0.5f, gapWidth = 0.dp),
            displayFeatures = displayFeatures
        )
    } else {
        Box(modifier = modifier.fillMaxWidth()) {
            NotesSinglePaneContent(
                notesUIState = notesUIState,
                noteLazyListState = noteLazyListState,
                modifier = Modifier.fillMaxSize(),
                closeDetailScreen = closeDetailScreen,
                navigateToDetail = { id, type ->
                    scope.launch {
                        stateVertical.animateScrollTo(0)
                    }
                    navigateToDetail(id, type)
                },
                addNote = addNote,
                stateVertical = stateVertical,
                tagsGrid = tagsGrid
            )
            if (navigationType == ChronologNavigationType.BOTTOM_NAVIGATION) {
                FloatingActionButton(
                    onClick = addNote,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(15.dp),
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                    contentColor = MaterialTheme.colorScheme.onTertiaryContainer
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = stringResource(id = R.string.edit),
                        modifier = Modifier.size(28.dp)
                    )
                }
            }
        }
    }
}
