package fi.metropolia.project.souvenirapp.model

import android.content.Context
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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
                Text("Location: ${memory.location}")
                Text("Date: ${memory.date}")
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
            location = "Random locaiton",
            date = "01-09-2022",
            light = 111.11f,
            imageUri = Uri.fromFile(File("file:///android_asset/strawberries.jpg")).toString()
        )
        list.add(memory)
    }
    return list
}