package com.cs346.chronolog

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.cs346.chronolog.data.getImageByFileName
import com.cs346.chronolog.data.painterResource
import com.cs346.chronolog.ui.category.CategoriesViewModel
import com.cs346.chronolog.ui.components.ChronologMenuBar
import com.cs346.chronolog.ui.login.LoginViewModel
import com.cs346.chronolog.ui.note.NotesViewModel
import com.cs346.chronolog.ui.theme.ChronologTheme
import com.cs346.chronolog.ui.utils.setMinSize
import moe.tlaster.precompose.PreComposeWindow
import moe.tlaster.precompose.ui.viewModel

@OptIn(ExperimentalMaterial3Api::class)
fun main() = application {
    val windowState = rememberWindowState(
        position = WindowPosition(alignment = Alignment.Center),
        size = DpSize(1000.dp, 750.dp),
    )
    PreComposeWindow(
        onCloseRequest = ::exitApplication,
        icon = painterResource(getImageByFileName("ic_launcher")),
        state = windowState,
        title = "Chronolog",
        undecorated = true,
        transparent = true
    ) {
        val notesViewModel = viewModel { NotesViewModel() }
        val categoriesViewModel = viewModel { CategoriesViewModel() }
        val loginViewModel = viewModel { LoginViewModel() }
        var darkTheme: Boolean by remember { mutableStateOf(false) }
        setMinSize()
        ChronologTheme(
            darkTheme = darkTheme
        ) {
            Card(
                shape = MaterialTheme.shapes.medium,
            ) {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    ChronologMenuBar(
                        modifier = Modifier,
                        windowState = windowState,
                        isDarkTheme = darkTheme,
                        switchTheme = { darkTheme = !darkTheme },
                        onCloseRequest = ::exitApplication
                    )
                    ChronologDesktopApp(
                        windowState = windowState,
                        notesViewModel = notesViewModel,
                        categoriesViewModel = categoriesViewModel,
                        loginViewModel = loginViewModel
                    )
                }
            }
        }
    }
}
