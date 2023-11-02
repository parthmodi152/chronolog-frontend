package com.cs346.chronolog.ui.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.window.layout.DisplayFeature
import com.google.accompanist.adaptive.HorizontalTwoPaneStrategy
import com.google.accompanist.adaptive.TwoPane
import com.cs346.chronolog.ui.category.CategoriesListScreen
import com.cs346.chronolog.ui.category.CategoriesSinglePaneContent
import com.cs346.chronolog.ui.category.CategoriesUIState
import com.cs346.chronolog.ui.category.CategoryNotesScreen
import com.cs346.chronolog.ui.utils.ChronologContentType

@Composable
fun CategoriesScreen(
    modifier: Modifier = Modifier,
    categoriesUIState: CategoriesUIState,
    contentType: ChronologContentType,
    closeDetailScreen: () -> Unit,
    displayFeatures: List<DisplayFeature>,
    navigateToDetail: (Long, ChronologContentType) -> Unit,
) {
    LaunchedEffect(key1 = contentType) {
        if ((contentType == ChronologContentType.SINGLE_PANE) && !categoriesUIState.isDetailOnlyOpen) {
            closeDetailScreen()
        }
    }
    val categoryLazyListState = rememberLazyListState()
    val noteLazyListState = rememberLazyListState()

    if (contentType == ChronologContentType.DUAL_PANE) {
        TwoPane(
            first = {
                CategoriesListScreen(
                    modifier = modifier,
                    categories = categoriesUIState.categories,
                    categoryLazyListState = categoryLazyListState,
                    navigateToDetail = navigateToDetail
                )
            },
            second = {
                CategoryNotesScreen(
                    notes = categoriesUIState.categoryNotes,
                    noteLazyListState = noteLazyListState,
                    isFullScreen = false,
                    onBackPressed = closeDetailScreen,
                    navigateToDetail = { _, _ -> }
                )
            },
            strategy = HorizontalTwoPaneStrategy(splitFraction = 0.5f, gapWidth = 0.dp),
            displayFeatures = displayFeatures
        )
    } else {
        CategoriesSinglePaneContent(
            modifier = Modifier.fillMaxSize(),
            categoriesUIState = categoriesUIState,
            categoryLazyListState = categoryLazyListState,
            noteLazyListState = noteLazyListState,
            closeDetailScreen = closeDetailScreen,
            navigateToDetail = navigateToDetail
        )
    }
}
