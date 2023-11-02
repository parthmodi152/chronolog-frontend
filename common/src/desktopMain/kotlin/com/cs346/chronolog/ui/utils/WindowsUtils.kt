package com.cs346.chronolog.ui.utils

import androidx.compose.ui.window.FrameWindowScope
import java.awt.Dimension

fun FrameWindowScope.setMinSize() {
    window.minimumSize = Dimension(540, 320)
}
