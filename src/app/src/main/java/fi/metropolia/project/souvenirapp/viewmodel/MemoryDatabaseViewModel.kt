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


class MemoryDatabaseViewModel(application: Application) : AndroidViewModel(application) {
    val database = MemoryDatabase.get(application)
    val memories = MutableLiveData<List<Memory>?>()

    fun getAll(): List<Memory> {
        return database.memoryDao().getAll()
    }

    fun insert(memory: Memory) {
        viewModelScope.launch {
            database.memoryDao().insert(memory)
        }
    }

    fun delete(memory: Memory) {
        viewModelScope.launch {
            database.memoryDao().delete(memory)
        }
    }

    fun update(memory: Memory) {
        viewModelScope.launch {
            database.memoryDao().update(memory)
        }
    }

    fun createNewMemory(
        title: String,
        description: String,
        latitude: Double,
        longitude: Double,
        imageUri: String,
    ) {
        val newMemory: Memory
        newMemory = Memory(0, title, description, latitude, longitude, imageUri)
        insert(newMemory)
    }

    fun setMemoriesFromDatabase() {
        viewModelScope.launch(Dispatchers.IO) {
            memories.postValue(withContext(Dispatchers.IO) {
                getAll()
            })
        }
    }


}