package fi.metropolia.project.souvenirapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import fi.metropolia.project.souvenirapp.model.data.Memory
import fi.metropolia.project.souvenirapp.model.data.MemoryDatabase
import kotlinx.coroutines.launch


class MemoryDatabaseViewModel(application: Application) : AndroidViewModel(application) {
    val database = MemoryDatabase.get(application)

    fun getAll() : List<Memory>{
        return database.memoryDao().getAll()
    }

    fun insert(memory: Memory){
        viewModelScope.launch {
            database.memoryDao().insert(memory)
        }
    }

    fun delete(memory: Memory){
        viewModelScope.launch {
            database.memoryDao().delete(memory)
        }
    }

    fun update(memory: Memory){
        viewModelScope.launch {
            database.memoryDao().update(memory)
        }
    }

}