package fi.metropolia.project.souvenirapp.model

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import fi.metropolia.project.souvenirapp.view.screens.assetsToBitmap
import fi.metropolia.project.souvenirapp.viewmodel.MemoryDatabaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@Composable
fun getBitmapFromSampleFile(): Bitmap? {
    val context = LocalContext.current
    return context.assetsToBitmap("strawberries.jpg")
}

fun logMemories(memoryDatabaseViewModel : MemoryDatabaseViewModel) {
    GlobalScope.launch {
        val memories = withContext(Dispatchers.IO) { memoryDatabaseViewModel.getAll() }
        Log.d("DBG", "memories: ${memories}")
    }
}
