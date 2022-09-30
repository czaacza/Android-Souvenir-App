package fi.metropolia.project.souvenirapp.model

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter

//Sample function to show list of Memories
@Composable
fun ShowMemories(memoriesList: List<Memory>) {
    Column() {
        memoriesList.forEach { memory ->
            Column() {
                Text("Title: ${memory.title}")
                Text("Description: ${memory.description}")
                Text("Location: ${memory.location}")
                Image(
                    bitmap = memory.image.asImageBitmap(),
                    contentDescription = "memory image description",
                )
            }
        }
    }

}