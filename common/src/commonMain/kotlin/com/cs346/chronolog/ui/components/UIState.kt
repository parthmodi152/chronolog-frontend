package com.cs346.chronolog.ui.components

import com.cs346.chronolog.data.model.Note

interface UIState {
    val notes: List<Note>
    val selectedNote: Note?
    val loading: Boolean
    val error: String?
    val isDetailOnlyOpen: Boolean
}
