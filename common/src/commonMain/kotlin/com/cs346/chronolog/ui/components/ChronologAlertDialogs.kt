package com.cs346.chronolog.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import com.cs346.chronolog.ui.edit.EditViewModel

@Composable
expect fun SaveNoteAlertDialog(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit = {},
    onConfirm: () -> Unit = {},
)

@Composable
expect fun NewNoteAlertDialog(
    editViewModel: EditViewModel,
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit = {},
    onConfirm: () -> Unit = {},
    onSubjectValueChange: (String) -> Unit,
    title: String,
    categories: List<String> = emptyList(),
    tags: List<String> = emptyList(),
    titleEditorFocus: FocusRequester
)
