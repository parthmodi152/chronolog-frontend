package com.cs346.chronolog.ui.contact


import com.cs346.chronolog.data.repository.AccountsRepository
import com.cs346.chronolog.data.repository.AccountsRepositoryImpl
import com.cs346.chronolog.ui.utils.ChronologContentType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope

class ContactsViewModel(
    private val accountsRepository: AccountsRepository = AccountsRepositoryImpl()
) : ViewModel() {
    private val _uiState = MutableStateFlow(ContactsUIState(loading = true))
    val uiState: StateFlow<ContactsUIState> = _uiState

    init {
        observerContacts()
    }

    private fun observerContacts() {
        viewModelScope.launch {
            accountsRepository.getAllContact()
                .catch { ex ->
                    _uiState.value = ContactsUIState(error = ex.message)
                }
                .collect { accounts ->
                    _uiState.value = ContactsUIState(
                        accounts = accounts,
                        selectedAccount = accounts.first()
                    )
                }
        }
    }

    fun setSelectedAccount(accountId: Long, contentType: ChronologContentType) {
        val account = uiState.value.accounts.find { it.id == accountId }
        _uiState.value = _uiState.value.copy(
            selectedAccount = account,
            isDetailOnlyOpen = contentType == ChronologContentType.SINGLE_PANE
        )
    }

    fun closeDetailScreen() {
        _uiState.value = _uiState.value.copy(
            isDetailOnlyOpen = false,
            selectedAccount = null
        )
    }
}
