package fi.metropolia.project.souvenirapp.model

import android.graphics.Bitmap
import android.hardware.Sensor
import android.hardware.SensorManager
import android.net.Uri
import android.os.Environment
import android.util.Log
import androidx.core.content.FileProvider
import fi.metropolia.project.souvenirapp.viewmodel.MemoryDatabaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File


fun logMemories(memoryDatabaseViewModel: MemoryDatabaseViewModel) {
    GlobalScope.launch {
        val memories = withContext(Dispatchers.IO) { memoryDatabaseViewModel.getAll() }
        memories.forEach{
        Log.d("DBG", "img: ${it.imageUri}")
        }
        Log.d("DBG", "-------------------------------------\n")
    }
}

/* TRY IF SENSOR IS ON THE PHONE (DON'T DELETE IT)*/
fun trysensor(sensorManager: SensorManager) {
    if (sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT) == null) {
        Log.d("DBG", "Error, Sensor does not exist.")
        false
    }
}


