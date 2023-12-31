package com.cs346.chronolog.ui.category

import com.cs346.chronolog.data.repository.CategoriesRepository
import com.cs346.chronolog.data.repository.CategoriesRepositoryImpl
import com.cs346.chronolog.data.repository.NotesRepository
import com.cs346.chronolog.data.repository.NotesRepositoryImpl
import com.cs346.chronolog.ui.utils.ChronologContentType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope

class CategoriesViewModel(
    private val categoriesRepository: CategoriesRepository = CategoriesRepositoryImpl(),
    private val notesRepository: NotesRepository = NotesRepositoryImpl(),
) : ViewModel() {
    private val _uiState = MutableStateFlow(CategoriesUIState(loading = true))
    val uiState: StateFlow<CategoriesUIState> = _uiState

    init {
        observerCategories()
        getCategoryNotes()
    }

    private fun observerCategories() {
        viewModelScope.launch {
            categoriesRepository.getAllCategories()
                .catch { ex ->
                    _uiState.value = CategoriesUIState(error = ex.message)
                }
                .collect { categories ->
                    _uiState.value = CategoriesUIState(
                        categories = categories,
                        selectedCategory = categories.first()
                    )
                }
        }
    }

    private fun getCategoryNotes() {
        viewModelScope.launch {
            uiState.value.selectedCategory?.let {
                notesRepository.getCategoryNotes(it)
                    .catch { ex ->
                        _uiState.value = CategoriesUIState(error = ex.message)
                    }
                    .collect { categoryNotes ->
                        _uiState.value = _uiState.value.copy(
                            categoryNotes = categoryNotes,
                        )
                    }
            }
        }
    }

    fun setSelectedCategory(categoryId: Long, contentType: ChronologContentType) {
        val category = uiState.value.categories.find { it.id == categoryId }
        _uiState.value = _uiState.value.copy(
            selectedCategory = category,
            isDetailOnlyOpen = contentType == ChronologContentType.SINGLE_PANE
        )
        getCategoryNotes()
    }

    fun closeDetailScreen() {
        _uiState.value = _uiState.value.copy(
            isDetailOnlyOpen = false,
            selectedCategory = null
        )
    }
}
