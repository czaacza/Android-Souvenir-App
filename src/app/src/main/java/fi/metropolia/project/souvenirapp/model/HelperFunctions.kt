package fi.metropolia.project.souvenirapp.model

import android.graphics.Bitmap
import android.hardware.Sensor
import android.hardware.SensorManager
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
/* TRY IF SENSOR IS ON THE PHONE (DON'T DELETE IT)*/
fun trysensor(sensorManager:SensorManager) {
    if (sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT) == null) {
        Log.d("DBG", "Error, Sensor does not exist.")
        false
    }
}
