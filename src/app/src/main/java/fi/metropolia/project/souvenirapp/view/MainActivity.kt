package fi.metropolia.project.souvenirapp.view

import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.preference.PreferenceManager
import fi.metropolia.project.souvenirapp.view.screens.getMap
import fi.metropolia.project.souvenirapp.view.theme.SouvenirAppTheme
import fi.metropolia.project.souvenirapp.viewmodel.MapViewModel
import fi.metropolia.project.souvenirapp.viewmodel.MemoryDatabaseViewModel
import fi.metropolia.project.souvenirapp.viewmodel.MemoryViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.osmdroid.config.Configuration
import java.io.File

class MainActivity : ComponentActivity() {

    private lateinit var mapViewModel: MapViewModel
    private lateinit var memoryDatabaseViewModel : MemoryDatabaseViewModel
    private lateinit var memoryViewModel: MemoryViewModel




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Configuration.getInstance()
            .load(application, PreferenceManager.getDefaultSharedPreferences(application))

        memoryDatabaseViewModel = MemoryDatabaseViewModel(application)
        memoryViewModel = MemoryViewModel(application, memoryDatabaseViewModel)

        

        setContent {
            mapViewModel = MapViewModel(application, getMap(context = applicationContext))
            SouvenirAppTheme {
                MainScreen(mapViewModel, memoryViewModel)
//                val memories = createSampleMemories()
//                ShowMemories(memoriesList = memories, applicationContext)
            }
        }
    }
}