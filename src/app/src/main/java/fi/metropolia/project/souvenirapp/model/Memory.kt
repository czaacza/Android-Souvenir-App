package fi.metropolia.project.souvenirapp.model

import android.content.res.AssetManager
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import fi.metropolia.project.souvenirapp.R

class Memory(
    val title: String,
    val description: String,
    val location: List<Double>,
    val image: Bitmap
) {

    companion object {
        // A function used to create sample list of memories
        fun createSampleMemories(assets: AssetManager): List<Memory> {
            val list = mutableListOf<Memory>()
            val inputStream = assets.open("strawberries.jpg")
            Log.d("DBG", "inputStream: ${inputStream}")
            val bitmap = BitmapFactory.decodeStream(inputStream)
            Log.d("DBG", "bitmap: ${bitmap}")
            for (i in 1..10) {
                val memory = Memory(
                    "memory${i}",
                    "memory${i} sample description",
                    listOf(Math.random() * 10 * i, Math.random() * 10 * i),
                    bitmap
                )
                list.add(memory)
            }
            return list
        }
    }
}