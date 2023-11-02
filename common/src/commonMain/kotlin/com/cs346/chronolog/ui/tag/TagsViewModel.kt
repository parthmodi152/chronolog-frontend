package com.cs346.chronolog.ui.tag

import com.cs346.chronolog.data.repository.NotesRepository
import com.cs346.chronolog.data.repository.NotesRepositoryImpl
import com.cs346.chronolog.data.repository.TagsRepository
import com.cs346.chronolog.data.repository.TagsRepositoryImpl
import com.cs346.chronolog.ui.utils.ChronologContentType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope

class TagsViewModel(
    private val tagsRepository: TagsRepository = TagsRepositoryImpl(),
    private val notesRepository: NotesRepository = NotesRepositoryImpl(),
) : ViewModel() {
    private val _uiState = MutableStateFlow(TagsUIState(loading = true))
    val uiState: StateFlow<TagsUIState> = _uiState

    init {
        observerTags()
        observerNotes()

    }

    private fun observerTags() {
        viewModelScope.launch {
            tagsRepository.getAllTags()
                .catch { ex ->
                    _uiState.value = TagsUIState(error = ex.message)
                }
                .collect { tags ->
                    _uiState.value = TagsUIState(
                        tags = tags
                    )
                }
        }
    }

    private fun observerNotes() {
        viewModelScope.launch {
            notesRepository.getAllNotes()
                .catch { ex ->
                    _uiState.value = TagsUIState(error = ex.message)
                }
                .collect { notes ->
                    _uiState.value = _uiState.value.copy(
                        notes = notes
                    )
                }
        }
    }

    fun setSelectedNote(noteId: Long, contentType: ChronologContentType) {
        val note = uiState.value.notes.find { it.id == noteId }
        _uiState.value = _uiState.value.copy(
            selectedNote = note,
            isDetailOnlyOpen = contentType == ChronologContentType.SINGLE_PANE
        )
    }

    fun closeDetailScreen() {
        _uiState.value = _uiState
            .value.copy(
                isDetailOnlyOpen = false,
                selectedNote = null
            )
    }
}
