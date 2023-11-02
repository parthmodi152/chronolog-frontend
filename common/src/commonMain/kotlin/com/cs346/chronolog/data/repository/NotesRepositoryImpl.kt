package com.cs346.chronolog.data.repository

import com.cs346.chronolog.data.model.Category
import com.cs346.chronolog.data.local.LocalNotesDataProvider
import com.cs346.chronolog.data.model.Note
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class NotesRepositoryImpl : NotesRepository {
    override fun getAllNotes(): Flow<List<Note>> = flow {
        emit(LocalNotesDataProvider.allNotes)
    }

    override fun getCategoryNotes(category: Category): Flow<List<Note>> = flow {
        val categoryNotes = LocalNotesDataProvider.allNotes.filter { it.category == category }
        emit(categoryNotes)
    }

    override fun getNoteFromId(id: Long): Flow<Note?> = flow {
        emit(LocalNotesDataProvider.allNotes.firstOrNull { it.id == id })
    }
}
