package com.cs346.chronolog.ui.screen

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowState
import com.cs346.chronolog.ui.components.UIState
import com.cs346.chronolog.ui.tag.TagsGrid
import com.cs346.chronolog.ui.tag.TagsUIState
import com.cs346.chronolog.ui.utils.ChronologContentType

@Composable
fun TagsScreen(
    modifier: Modifier = Modifier,
    windowState: WindowState,
    tagsUIState: UIState,
    closeDetailScreen: () -> Unit,
    addNote: () -> Unit,
    onClickChip: (Long) -> Unit = {},
    navigateToDetail: (Long, ChronologContentType) -> Unit,

    ) {
    val stateHorizontal: ScrollState = rememberScrollState()
    NotesScreen(
        modifier = modifier,
        windowState = windowState,
        notesUIState = tagsUIState,
        addNote = addNote,
        closeDetailScreen = closeDetailScreen,
        navigateToDetail = navigateToDetail,
        tagsGrid = {
            Column {
                TagsGrid(
                    tagsUIState = tagsUIState as TagsUIState,
                    onClickChip = onClickChip,
                    stateHorizontal = stateHorizontal
                )
                HorizontalScrollbar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    adapter = rememberScrollbarAdapter(stateHorizontal),
                    style = LocalScrollbarStyle.current.copy(
                        hoverColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        unhoverColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                )
            }
        }
    )
}
