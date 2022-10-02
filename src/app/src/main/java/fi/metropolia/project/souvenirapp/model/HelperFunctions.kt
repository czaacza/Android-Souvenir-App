package fi.metropolia.project.souvenirapp.model

import android.graphics.Bitmap
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import fi.metropolia.project.souvenirapp.view.screens.assetsToBitmap


@Composable
fun getBitmapFromSampleFile(): Bitmap? {
    val context = LocalContext.current
    return context.assetsToBitmap("strawberries.jpg")
}

