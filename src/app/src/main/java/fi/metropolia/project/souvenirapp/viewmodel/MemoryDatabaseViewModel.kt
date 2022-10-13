package fi.metropolia.project.souvenirapp.viewmodel

import android.app.Application
import android.graphics.Bitmap
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import fi.metropolia.project.souvenirapp.model.data.MemoryDatabase
import fi.metropolia.project.souvenirapp.model.data.Memory
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

    fun getAll(): List<Memory> {
        return database.memoryDao().getAll()
    }

    suspend fun insert(memory: Memory) {
        database.memoryDao().insert(memory)
    }

    fun delete(memory: Memory) {
        viewModelScope.launch {
            database.memoryDao().delete(memory)
            loadMemoriesFromDatabase()
        }
    }

    suspend fun update(memory: Memory) {
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

            val newMemory =
                Memory(0, title, description, location, currentDate, light, fileAbsolutePath)

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

    suspend fun loadMemoriesFromDatabase() {
        memories.postValue(withContext(Dispatchers.IO) {
            getAll()
        }!!)
    }


}