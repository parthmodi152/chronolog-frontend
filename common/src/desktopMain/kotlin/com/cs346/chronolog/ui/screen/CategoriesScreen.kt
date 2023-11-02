package com.cs346.chronolog.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowState
import com.cs346.chronolog.ui.category.CategoriesListScreen
import com.cs346.chronolog.ui.category.CategoriesSinglePaneContent
import com.cs346.chronolog.ui.category.CategoriesUIState
import com.cs346.chronolog.ui.category.CategoryNotesScreen
import com.cs346.chronolog.ui.components.ChronologTwoPane
import com.cs346.chronolog.ui.utils.ChronologContentType

@Composable
fun CategoriesScreen(
    modifier: Modifier = Modifier,
    categoriesUIState: CategoriesUIState,
    windowState: WindowState,
    closeDetailScreen: () -> Unit,
    navigateToDetail: (Long, ChronologContentType) -> Unit,
) {
    val categoryLazyListState = rememberLazyListState()
    val noteLazyListState = rememberLazyListState()
    val isTwoPane: Boolean = windowState.size.width > 850.dp

    AnimatedVisibility(
        visible = isTwoPane,
        enter = expandHorizontally(initialWidth = { it * 5 / 13 }),
        exit = shrinkHorizontally(targetWidth = { it * 5 / 13 }) + fadeOut()
    ) {
        ChronologTwoPane(
            modifier = modifier,
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
            }
        )
    }
    AnimatedVisibility(
        visible = !isTwoPane,
        enter = expandHorizontally(initialWidth = { it * 5 / 13 }),
        exit = shrinkHorizontally(targetWidth = { it * 5 / 13 })
    ) {
        CategoriesSinglePaneContent(
            modifier = modifier.fillMaxSize(),
            categoriesUIState = categoriesUIState,
            categoryLazyListState = categoryLazyListState,
            noteLazyListState = noteLazyListState,
            closeDetailScreen = closeDetailScreen,
            navigateToDetail = navigateToDetail
        )
    }
}
