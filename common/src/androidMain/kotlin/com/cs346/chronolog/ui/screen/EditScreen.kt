package com.cs346.chronolog.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.input.key.*
import androidx.compose.ui.unit.dp
import androidx.window.layout.DisplayFeature
import com.google.accompanist.adaptive.HorizontalTwoPaneStrategy
import com.google.accompanist.adaptive.TwoPane
import com.cs346.chronolog.ui.components.NewNoteAlertDialog
import com.cs346.chronolog.ui.components.SaveNoteAlertDialog
import com.cs346.chronolog.ui.components.ChronologTopBar
import com.cs346.chronolog.ui.edit.EditContent
import com.cs346.chronolog.ui.edit.EditSinglePaneScreen
import com.cs346.chronolog.ui.edit.EditViewModel
import com.cs346.chronolog.ui.note.NoteContent
import com.cs346.chronolog.ui.utils.ChronologContentType

@OptIn(ExperimentalComposeUiApi::class, ExperimentalAnimationApi::class)
@Composable
fun EditScreen(
    editViewModel: EditViewModel,
    modifier: Modifier = Modifier,
    contentType: ChronologContentType,
    displayFeatures: List<DisplayFeature>,
    title: String,
    text: String,
    categories: List<String> = emptyList(),
    tags: List<String> = emptyList(),
    onBodyValueChange: (String) -> Unit = {},
    onSubjectValueChange: (String) -> Unit = {},
    isEditModeEnabled: Boolean = true,
    isOpenNoteInfoDialog: Boolean = false,
    isOpenSaveDialog: Boolean = false,
    titleEditorFocus: FocusRequester = remember { FocusRequester() },
    onModeChanged: () -> Unit = {},
    onSavedNote: () -> Unit = {},
    onDismissRequest: () -> Unit = {},
    onDismissSaveRequest: () -> Unit = {},
    onConfirm: () -> Unit = {},
    onConfirmClose: () -> Unit = {},
    onClickedTextButton: (char: String) -> Unit = { _ -> },
    onBackPressed: () -> Unit = {},
    onClickTitle: () -> Unit = {},
) {
    AnimatedVisibility(
        visible = isOpenNoteInfoDialog,
        enter = scaleIn(),
        exit = scaleOut(),
    ) {
        NewNoteAlertDialog(
            editViewModel = editViewModel,
            modifier = Modifier,
            onDismissRequest = onDismissRequest,
            onConfirm = onConfirm,
            title = title,
            onSubjectValueChange = onSubjectValueChange,
            titleEditorFocus = titleEditorFocus,
            categories = categories,
            tags = tags
        )
    }

    AnimatedVisibility(isOpenSaveDialog) {
        SaveNoteAlertDialog(
            modifier = Modifier,
            onDismissRequest = onDismissSaveRequest,
            onConfirm = onConfirmClose,
        )
    }
    if (contentType == ChronologContentType.DUAL_PANE) {
        Column(
            modifier = modifier.fillMaxSize().onPreviewKeyEvent {
                when {
                    (it.isCtrlPressed && it.key == Key.S && it.type == KeyEventType.KeyUp) -> {
                        onSavedNote() // Ctrl+S: Save
                        true
                    }

                    (it.key == Key.Escape && it.type == KeyEventType.KeyUp) -> {
                        onBackPressed() // Esc: Quit
                        true
                    }

                    else -> false
                }
            }
        ) {
            ChronologTopBar(
                title = title,
                isFullScreen = true,
                onBackPressed = onBackPressed,
                onClickTitle = onClickTitle
            ) {
                IconButton(onClick = onSavedNote) {
                    Icon(
                        imageVector = Icons.Default.Save,
                        contentDescription = "preview",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            TwoPane(
                first = {
                    EditContent(
                        text = text,
                        onBodyValueChange = onBodyValueChange,
                        onClickedTextButton = onClickedTextButton
                    )
                },
                second = {
                    NoteContent(noteBody = text)
                },
                strategy = HorizontalTwoPaneStrategy(splitFraction = 0.5f, gapWidth = 0.dp),
                displayFeatures = displayFeatures
            )
        }
    } else {
        EditSinglePaneScreen(
            modifier = modifier.fillMaxSize(),
            title = title,
            text = text,
            onBodyValueChange = onBodyValueChange,
            isEditModeEnabled = isEditModeEnabled,
            onModeChanged = onModeChanged,
            onSavedNote = onSavedNote,
            onClickedTextButton = onClickedTextButton,
            onBackPressed = onBackPressed,
            onClickTitle = onClickTitle
        )
    }
}
