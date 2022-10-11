package fi.metropolia.project.souvenirapp.viewmodel

import android.R.attr
import android.app.Application
import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import fi.metropolia.project.souvenirapp.model.data.Memory
import fi.metropolia.project.souvenirapp.model.data.MemoryDatabase
import fi.metropolia.project.souvenirapp.model.data.MemoryEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.*


class MemoryDatabaseViewModel(application: Application) : AndroidViewModel(application) {
    val app = application
    val database = MemoryDatabase.get(application)
    val memories = MutableLiveData<List<Memory>>()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            loadMemoriesFromDatabase()
        }
    }

    fun getAll(): List<MemoryEntity> {
        return database.memoryDao().getAll()
    }

    suspend fun insert(memory: MemoryEntity) {
        database.memoryDao().insert(memory)
    }

    suspend fun delete(memory: MemoryEntity) {
        viewModelScope.launch {
            database.memoryDao().delete(memory)
        }
    }

    suspend fun update(memory: MemoryEntity) {
        viewModelScope.launch {
            database.memoryDao().update(memory)
        }
    }

    fun clear() {
        viewModelScope.launch {
            database.memoryDao().clearTable();
        }
    }

    fun createNewMemory(
        title: String,
        description: String,
        location: String,
        light: Float,
        bitmap: Bitmap,
    ) {
        viewModelScope.launch(Dispatchers.Default) {
            val file = getFileFromBitmap(bitmap)
            val fileAbsolutePath = file.absolutePath

            val currentDate: String =
                SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())

            val newMemory: MemoryEntity
            newMemory =
                MemoryEntity(0, title, description, location, currentDate, light, fileAbsolutePath)

            insert(newMemory)

            loadMemoriesFromDatabase()

        }
    }

    fun getFileFromBitmap(bitmap: Bitmap): File {
        val randomId: Int = (Math.random() * Math.random() * 10000000000).toInt()
        val file = File(app.cacheDir, "memory${randomId}")

        val outputStream: OutputStream = BufferedOutputStream(FileOutputStream(file))
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        outputStream.close()

        return file
    }

    fun memoryEntitiesToMemories(memoryEntities: List<MemoryEntity>): List<Memory> {
        val memories = mutableListOf<Memory>()
        memoryEntities.forEach { memoryEntity ->
            val memory: Memory = Memory(
                memoryEntity.id,
                memoryEntity.title,
                memoryEntity.description,
                memoryEntity.location,
                memoryEntity.date,
                memoryEntity.light,
                memoryEntity.imageUri
            )
            memories.add(memory)
        }
        return memories
    }

    suspend fun loadMemoriesFromDatabase() {
        Log.d("DBG", "loading memories")
        memories.postValue(withContext(Dispatchers.IO) {
            memoryEntitiesToMemories(getAll())
        }!!)
    }


}