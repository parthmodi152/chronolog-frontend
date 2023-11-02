package com.cs346.chronolog.ui.note

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun NoteContent(
    modifier: Modifier = Modifier,
    noteBody: String,
    stateVertical: ScrollState = rememberScrollState(),
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp, vertical = 10.dp)
            .background(
                color = MaterialTheme.colorScheme.surfaceVariant,
                shape = MaterialTheme.shapes.large
            )
            .verticalScroll(stateVertical)
            .focusable()
    ) {
        Text(
            modifier = modifier.padding(horizontal = 20.dp, vertical = 12.dp),
            text = noteBody,
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}
