package com.cs346.chronolog.ui.note

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.cs346.chronolog.data.model.Note
import com.cs346.chronolog.ui.components.ChronologSearchBar
import com.cs346.chronolog.ui.components.ChronologTopBar
import com.cs346.chronolog.ui.utils.ChronologContentType

@Composable
fun NoteListScreen(
    notes: List<Note>,
    noteLazyListState: LazyListState,
    modifier: Modifier = Modifier,
    navigateToDetail: (Long, ChronologContentType) -> Unit,
) {
    LazyColumn(modifier = modifier, state = noteLazyListState) {
        item {
            Column {
                ChronologSearchBar(
                    modifier = Modifier.fillMaxWidth(),
                    searchContent = "Search Notes"
                )
            }
        }
        items(items = notes, key = { it.id }) { note ->
            NotesListItem(note = note) { noteId ->
                navigateToDetail(noteId, ChronologContentType.SINGLE_PANE)
            }
        }
    }
}

@Composable
fun NoteDetailScreen(
    modifier: Modifier = Modifier,
    note: Note,
    isFullScreen: Boolean = true,
    onBackPressed: () -> Unit = {},
    stateVertical: ScrollState = rememberScrollState()
) {
    Column(modifier = modifier.fillMaxSize()) {
        ChronologTopBar(
            title = note.subject,
            isFullScreen = isFullScreen,
            onBackPressed = onBackPressed
        ) {
            IconButton(onClick = {  }) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        NoteContent(
            noteBody = note.body,
            stateVertical = stateVertical,
        )
    }
}
