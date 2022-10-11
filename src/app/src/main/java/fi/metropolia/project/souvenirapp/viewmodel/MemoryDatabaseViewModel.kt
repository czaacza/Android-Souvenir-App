package fi.metropolia.project.souvenirapp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import fi.metropolia.project.souvenirapp.model.data.Memory
import fi.metropolia.project.souvenirapp.model.data.MemoryEntity
import fi.metropolia.project.souvenirapp.model.data.MemoryDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*


class MemoryDatabaseViewModel(application: Application) : AndroidViewModel(application) {
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
        imageUri: String,
    ) {
        val currentDate: String = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())

        val newMemory: MemoryEntity
        newMemory = MemoryEntity(0, title, description, location, currentDate, light, imageUri)

        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.IO) {
                insert(newMemory)
            }

            loadMemoriesFromDatabase()
        }
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