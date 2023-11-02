package com.cs346.chronolog.ui.contact

import com.cs346.chronolog.data.model.Account

data class ContactsUIState(
    val accounts: List<Account> = emptyList(),
    val selectedAccount: Account? = null,
    val isDetailOnlyOpen: Boolean = false,
    val loading: Boolean = false,
    val error: String? = null
)
