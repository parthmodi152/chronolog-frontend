package com.cs346.chronolog.data

import androidx.compose.ui.graphics.painter.Painter
import com.cs346.chronolog.MR
import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.getImageByFileName

fun getImageByFileName(name: String): ImageResource {
    val fallbackImage = MR.images.avatar_0
    return MR.images.getImageByFileName(name)
        ?: MR.images.getImageByFileName("$name.jpg")
        ?: MR.images.getImageByFileName("$name.png")
        ?: fallbackImage
}

expect fun painterResource(imageResource: ImageResource): Painter
