package com.cs346.chronolog.ui.screen

import androidx.compose.runtime.Composable
import androidx.window.layout.DisplayFeature
import com.cs346.chronolog.ui.components.UIState
import com.cs346.chronolog.ui.tag.TagsGrid
import com.cs346.chronolog.ui.tag.TagsUIState
import com.cs346.chronolog.ui.utils.ChronologContentType
import com.cs346.chronolog.ui.utils.ChronologNavigationType

@Composable
fun TagsScreen(
    contentType: ChronologContentType,
    tagsUIState: UIState,
    navigationType: ChronologNavigationType,
    displayFeatures: List<DisplayFeature>,
    closeDetailScreen: () -> Unit,
    addNote: () -> Unit,
    onClickChip: (Long) -> Unit = {},
    navigateToDetail: (Long, ChronologContentType) -> Unit,
) {
    NoteScreen(
        contentType = contentType,
        notesUIState = tagsUIState,
        navigationType = navigationType,
        displayFeatures = displayFeatures,
        addNote = addNote,
        closeDetailScreen = closeDetailScreen,
        navigateToDetail = navigateToDetail,
        tagsGrid = {
            TagsGrid(tagsUIState = tagsUIState as TagsUIState) {
                onClickChip(it)
            }
        }
    )
}
