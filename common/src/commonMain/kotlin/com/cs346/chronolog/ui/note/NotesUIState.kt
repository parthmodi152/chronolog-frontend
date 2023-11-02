package com.cs346.chronolog.ui.note

import com.cs346.chronolog.data.model.Note
import com.cs346.chronolog.ui.components.UIState

data class NotesUIState(
    override val notes: List<Note> = emptyList(),
    override val selectedNote: Note? = null,
    override val isDetailOnlyOpen: Boolean = false,
    val isEditModeEnabled: Boolean = false,
    override val loading: Boolean = false,
    override val error: String? = null
) : UIState
