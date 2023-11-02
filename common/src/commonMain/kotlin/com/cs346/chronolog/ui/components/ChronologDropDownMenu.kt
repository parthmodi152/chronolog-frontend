package com.cs346.chronolog.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
expect fun ChronologDropDownMenu(
    modifier: Modifier = Modifier,
    label: String = "Category",
    menuList: List<String>,
)



