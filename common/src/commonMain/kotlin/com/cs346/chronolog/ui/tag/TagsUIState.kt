package com.cs346.chronolog.ui.tag

import com.cs346.chronolog.data.model.Note
import com.cs346.chronolog.data.model.Tag
import com.cs346.chronolog.ui.components.UIState

data class TagsUIState(
    val tags: List<Tag> = emptyList(),
    override val notes: List<Note> = emptyList(),
    override val selectedNote: Note? = null,
    override val isDetailOnlyOpen: Boolean = false,
    override val loading: Boolean = false,
    override val error: String? = null,
) : UIState
