package com.cs346.chronolog.ui.edit

import com.cs346.chronolog.data.model.Account
import com.cs346.chronolog.data.model.Category
import com.cs346.chronolog.data.model.Note
import com.cs346.chronolog.data.model.Tag

data class EditUIState(
    val notes: List<Note> = emptyList(),
    val account: Account? = null,
    val categories: List<Category> = emptyList(),
    val tags: List<Tag> = emptyList(),
    val loading: Boolean = false,
    val error: String? = null
)

enum class EditScreenMode {
    EDIT_NOTE_MODE, ADD_NOTE_MODE
}
