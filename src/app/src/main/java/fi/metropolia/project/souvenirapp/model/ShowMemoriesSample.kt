package fi.metropolia.project.souvenirapp.model

import android.content.ContentResolver
import android.content.Context
import android.content.res.AssetManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.asImageBitmap
import fi.metropolia.project.souvenirapp.model.data.Memory
import java.io.File

//Sample function to show list of Memories
@Composable
fun ShowMemories(memoriesList: List<Memory>, context: Context) {
    Column() {
        memoriesList.forEach { memory ->
            Column() {
                Text("Title: ${memory.title}")
                Text("Description: ${memory.description}")
                Text("Location: (${memory.latitude} ${memory.longitude})")
//                Image(
//                    bitmap = bitmap.asImageBitmap(),
//                    contentDescription = "memory image description",
//                )
            }
        }
    }
}

// A function used to create sample list of memories
fun createSampleMemories(): List<Memory> {
    val list = mutableListOf<Memory>()
    for (i in 1..10) {
        val memory = Memory(
            id = i,
            title = "memory${i}",
            description = "memory${i} sample description",
            latitude = Math.random() * 10 * i,
            longitude = Math.random() * 10 * i,
            imageUri = Uri.fromFile(File("file:///android_asset/strawberries.jpg")).toString()
        )
        list.add(memory)
    }
    return list
}