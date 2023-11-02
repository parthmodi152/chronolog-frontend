package com.cs346.chronolog

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.cs346.chronolog.data.local.LocalCategoriesDataProvider
import com.cs346.chronolog.data.local.LocalNotesDataProvider
import com.cs346.chronolog.ui.category.CategoriesListScreen
import com.cs346.chronolog.ui.note.NoteListScreen

@Composable
fun App() {
    Row(
        modifier = Modifier.fillMaxSize()
    ) {
        NoteListScreen(
            modifier = Modifier.weight(1f),
            notes = LocalNotesDataProvider.allNotes,
            navigateToDetail = { _, _ -> },
            noteLazyListState = rememberLazyListState()
        )
        CategoriesListScreen(
            modifier = Modifier.weight(1f),
            categories = LocalCategoriesDataProvider.allCategory,
            categoryLazyListState = rememberLazyListState()
        )
    }
}
