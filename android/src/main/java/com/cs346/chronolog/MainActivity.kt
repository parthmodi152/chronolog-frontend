package com.cs346.chronolog

import android.os.Bundle
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import com.google.accompanist.adaptive.calculateDisplayFeatures
import com.cs346.chronolog.ui.ChronologApp
import com.cs346.chronolog.ui.category.CategoriesViewModel
import com.cs346.chronolog.ui.note.NotesViewModel
import com.cs346.chronolog.ui.theme.ChronologTheme
import moe.tlaster.precompose.lifecycle.PreComposeActivity
import moe.tlaster.precompose.lifecycle.setContent
import moe.tlaster.precompose.ui.viewModel

class MainActivity : PreComposeActivity() {

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ChronologTheme {
                val windowSize = calculateWindowSizeClass(this)
                val displayFeatures = calculateDisplayFeatures(this)

                val notesViewModel = viewModel { NotesViewModel() }
                val categoriesViewModel = viewModel { CategoriesViewModel() }

                ChronologApp(
                    windowSize = windowSize,
                    displayFeatures = displayFeatures,
                    notesViewModel = notesViewModel,
                    categoriesViewModel = categoriesViewModel,
                )
            }
        }
    }
}
