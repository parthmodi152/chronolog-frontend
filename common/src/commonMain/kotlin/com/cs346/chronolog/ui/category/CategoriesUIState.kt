package com.cs346.chronolog.ui.category

import com.cs346.chronolog.data.model.Category
import com.cs346.chronolog.data.model.Note

data class CategoriesUIState(
    val categories: List<Category> = emptyList(),
    val categoryNotes: List<Note> = emptyList(),
    val selectedCategory: Category? = null,
    val isDetailOnlyOpen: Boolean = false,
    val loading: Boolean = false,
    val error: String? = null
)
