package com.cs346.chronolog

import android.os.Bundle
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import com.google.accompanist.adaptive.calculateDisplayFeatures
import com.cs346.chronolog.ui.ChronologApp
import com.cs346.chronolog.ui.category.CategoriesViewModel
import com.cs346.chronolog.ui.contact.ContactsViewModel
import com.cs346.chronolog.ui.edit.EditViewModel
import com.cs346.chronolog.ui.note.NotesViewModel
import com.cs346.chronolog.ui.tag.TagsViewModel
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
                val editViewModel = viewModel { EditViewModel() }
                val contactsViewModel = viewModel { ContactsViewModel() }
                val categoriesViewModel = viewModel { CategoriesViewModel() }
                val tagsViewModel = viewModel { TagsViewModel() }

                ChronologApp(
                    windowSize = windowSize,
                    displayFeatures = displayFeatures,
                    notesViewModel = notesViewModel,
                    contactsViewModel = contactsViewModel,
                    categoriesViewModel = categoriesViewModel,
                    tagsViewModel = tagsViewModel,
                    editViewModel = editViewModel,
                )
            }
        }
    }
}
