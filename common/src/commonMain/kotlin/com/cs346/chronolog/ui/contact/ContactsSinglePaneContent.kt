package com.cs346.chronolog.ui.contact

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.cs346.chronolog.ui.utils.ChronologContentType
import moe.tlaster.precompose.navigation.BackHandler

@Composable
fun ContactsSinglePaneContent(
    modifier: Modifier = Modifier,
    contactsUIState: ContactsUIState,
    contactLazyListState: LazyListState,
    closeDetailScreen: () -> Unit,
    navigateToDetail: (Long, ChronologContentType) -> Unit
) {
    val detailVisibility: Boolean =
        contactsUIState.selectedAccount != null && contactsUIState.isDetailOnlyOpen
    AnimatedVisibility(
        visible = !detailVisibility,
        enter = slideInHorizontally(initialOffsetX = { -it }),
        exit = slideOutHorizontally(targetOffsetX = { -it }) + fadeOut()
    ) {
        ContactListScreen(
            accounts = contactsUIState.accounts,
            contactLazyListState = contactLazyListState,
            modifier = modifier,
            navigateToDetail = navigateToDetail
        )
    }
    AnimatedVisibility(
        visible = detailVisibility,
        enter = slideInHorizontally(initialOffsetX = { it }),
        exit = slideOutHorizontally(targetOffsetX = { it }) + fadeOut()
    ) {
        BackHandler {
            closeDetailScreen()
        }
        contactsUIState.selectedAccount?.let {
            ContactDetailScreen(account = it) {
                closeDetailScreen()
            }
        }
    }
}
