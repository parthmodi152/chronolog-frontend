package com.cs346.chronolog.data.repository

import com.cs346.chronolog.data.model.Category
import com.cs346.chronolog.data.model.Note
import kotlinx.coroutines.flow.Flow

interface NotesRepository {
    fun getAllNotes(): Flow<List<Note>>
    fun getCategoryNotes(category: Category): Flow<List<Note>>
    fun getNoteFromId(id: Long): Flow<Note?>
}
