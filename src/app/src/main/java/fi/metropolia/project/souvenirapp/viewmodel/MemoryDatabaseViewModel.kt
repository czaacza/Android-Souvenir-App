package fi.metropolia.project.souvenirapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import fi.metropolia.project.souvenirapp.model.data.Memory
import fi.metropolia.project.souvenirapp.model.data.MemoryDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*


class MemoryDatabaseViewModel(application: Application) : AndroidViewModel(application) {
    val database = MemoryDatabase.get(application)
    val memories = MutableLiveData<List<Memory>?>()

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

    suspend fun delete(memory: Memory) {
        viewModelScope.launch {
            database.memoryDao().delete(memory)
        }
    }

    suspend fun update(memory: Memory) {
        viewModelScope.launch {
            database.memoryDao().update(memory)
        }
    }

    suspend fun clear() {
        viewModelScope.launch {
            database.memoryDao().clearTable();
        }
    }

    fun createNewMemory(
        title: String,
        description: String,
        location: String,
        light: Float,
        imageUri: String,
    ) {
        val currentDate: String = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())

        val newMemory: Memory
        newMemory = Memory(0, title, description, location, currentDate, light, imageUri)

        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.IO) {
                insert(newMemory)
            }

            loadMemoriesFromDatabase()
        }
    }

    suspend fun loadMemoriesFromDatabase() {
        memories.postValue(withContext(Dispatchers.IO) {
            getAll()
        })
    }


}