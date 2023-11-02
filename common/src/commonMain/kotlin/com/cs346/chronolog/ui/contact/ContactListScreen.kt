package com.cs346.chronolog.ui.contact

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.cs346.chronolog.data.model.Account
import com.cs346.chronolog.ui.components.ChronologTopBar
import com.cs346.chronolog.ui.components.ChronologSearchBar
import com.cs346.chronolog.ui.utils.ChronologContentType


@Composable
fun ContactListScreen(
    modifier: Modifier = Modifier,
    accounts: List<Account>,
    contactLazyListState: LazyListState,
    navigateToDetail: (Long, ChronologContentType) -> Unit = { _, _ -> }
) {
    LazyColumn(modifier = modifier, state = contactLazyListState) {
        item {
            ChronologSearchBar(
                modifier = Modifier.fillMaxWidth(),
                searchContent = "Search Contacts"
            )
        }
        items(items = accounts, key = { it.id }) { account ->
            ContactsListItem(account = account) { accountId ->
                navigateToDetail(accountId, ChronologContentType.SINGLE_PANE)
            }
        }
    }
}

@Composable
fun ContactDetailScreen(
    modifier: Modifier = Modifier,
    account: Account,
    isFullScreen: Boolean = true,
    onBackPressed: () -> Unit = {}
) {
    Column(modifier = modifier.fillMaxSize()) {
        ChronologTopBar(
            title = account.fullName,
            isFullScreen = isFullScreen,
            onBackPressed = onBackPressed
        )
        ContactContent(account = account)
    }
}
