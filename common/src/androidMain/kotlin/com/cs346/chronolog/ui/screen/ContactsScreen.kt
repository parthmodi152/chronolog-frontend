package com.cs346.chronolog.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.window.layout.DisplayFeature
import com.google.accompanist.adaptive.HorizontalTwoPaneStrategy
import com.google.accompanist.adaptive.TwoPane
import com.cs346.chronolog.ui.contact.ContactDetailScreen
import com.cs346.chronolog.ui.contact.ContactListScreen
import com.cs346.chronolog.ui.contact.ContactsSinglePaneContent
import com.cs346.chronolog.ui.contact.ContactsUIState
import com.cs346.chronolog.ui.utils.ChronologContentType

@Composable
fun ContactsScreen(
    modifier: Modifier = Modifier,
    contactsUIState: ContactsUIState,
    contentType: ChronologContentType,
    closeDetailScreen: () -> Unit,
    displayFeatures: List<DisplayFeature>,
    navigateToDetail: (Long, ChronologContentType) -> Unit
) {
    LaunchedEffect(key1 = contentType) {
        if ((contentType == ChronologContentType.SINGLE_PANE) && !contactsUIState.isDetailOnlyOpen) {
            closeDetailScreen()
        }
    }
    val contactLazyListState = rememberLazyListState()

    if (contentType == ChronologContentType.DUAL_PANE) {
        TwoPane(
            first = {
                ContactListScreen(
                    accounts = contactsUIState.accounts,
                    contactLazyListState = contactLazyListState,
                    navigateToDetail = navigateToDetail
                )
            },
            second = {
                ContactDetailScreen(
                    account = contactsUIState.selectedAccount ?: contactsUIState.accounts.first(),
                    isFullScreen = false
                )
            },
            strategy = HorizontalTwoPaneStrategy(splitFraction = 0.5f, gapWidth = 0.dp),
            displayFeatures = displayFeatures
        )
    } else {
        Box(modifier = modifier.fillMaxSize()) {
            ContactsSinglePaneContent(
                modifier = Modifier.fillMaxSize(),
                contactsUIState = contactsUIState,
                contactLazyListState = contactLazyListState,
                closeDetailScreen = closeDetailScreen,
                navigateToDetail = navigateToDetail
            )
        }
    }
}
