package fi.metropolia.project.souvenirapp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import fi.metropolia.project.souvenirapp.model.createSampleMemories
import fi.metropolia.project.souvenirapp.model.data.Memory

class MemoryViewModel() : ViewModel() {
    val memories = mutableListOf<Memory>()
    val memoriesLiveData = MutableLiveData<MutableList<Memory>>(memories)
    val newMemory = MutableLiveData<Memory>()

    fun createNewMemory(
        title: String,
        description: String,
        latitude: Double,
        longitude: Double,
        imageUri: String
    ) {
        Log.d("DBG", "Created new Memory!")
        newMemory.value = Memory(0, title, description, latitude, longitude, imageUri)
        memories.add(newMemory.value!!)
        memoriesLiveData.value = memories
    }
}