package com.cs346.chronolog.ui.category

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.cs346.chronolog.data.model.Category
import com.cs346.chronolog.data.model.Note
import com.cs346.chronolog.ui.components.ChronologListItem
import com.cs346.chronolog.ui.components.ChronologSearchBar
import com.cs346.chronolog.ui.components.ChronologTopBar
import com.cs346.chronolog.ui.note.NotesListItem
import com.cs346.chronolog.ui.utils.ChronologContentType


@Composable
fun CategoriesListScreen(
    modifier: Modifier = Modifier,
    categories: List<Category>,
    categoryLazyListState: LazyListState,
    navigateToDetail: (Long, ChronologContentType) -> Unit = { _, _ -> }
) {
    LazyColumn(modifier = modifier, state = categoryLazyListState) {
        item {
            ChronologSearchBar(
                modifier = Modifier.fillMaxWidth(),
                searchContent = "Search Categories"
            )
        }
        items(items = categories, key = { it.id }) { category ->
            ChronologListItem(category = category) { categoryId ->
                navigateToDetail(categoryId, ChronologContentType.SINGLE_PANE)
            }
        }
    }
}

@Composable
fun CategoryNotesScreen(
    modifier: Modifier = Modifier,
    notes: List<Note>,
    isFullScreen: Boolean = true,
    noteLazyListState: LazyListState,
    onBackPressed: () -> Unit = {},
    navigateToDetail: (Long, ChronologContentType) -> Unit
) {
    Column(modifier = modifier.fillMaxSize()) {
        ChronologTopBar(
            title = notes.first().category.name,
            isFullScreen = isFullScreen,
            onBackPressed = onBackPressed
        )
        LazyColumn(modifier = modifier, state = noteLazyListState) {
            items(items = notes, key = { it.id }) { note ->
                NotesListItem(note = note) { noteId ->
                    navigateToDetail(noteId, ChronologContentType.SINGLE_PANE)
                }
            }
        }
    }
}

