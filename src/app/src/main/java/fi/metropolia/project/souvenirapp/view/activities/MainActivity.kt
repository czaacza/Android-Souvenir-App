package fi.metropolia.project.souvenirapp.view.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.preference.PreferenceManager
import fi.metropolia.project.souvenirapp.view.MainScreen
import fi.metropolia.project.souvenirapp.view.screens.getMap
import fi.metropolia.project.souvenirapp.view.theme.SouvenirAppTheme
import fi.metropolia.project.souvenirapp.viewmodel.CameraViewModel
import fi.metropolia.project.souvenirapp.viewmodel.MapViewModel
import fi.metropolia.project.souvenirapp.viewmodel.MemoryDatabaseViewModel
import org.osmdroid.config.Configuration

class MainActivity : ComponentActivity() {

    private lateinit var mapViewModel: MapViewModel
    private lateinit var memoryDatabaseViewModel : MemoryDatabaseViewModel
    private lateinit var cameraViewModel : CameraViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Configuration.getInstance()
            .load(application, PreferenceManager.getDefaultSharedPreferences(application))

        memoryDatabaseViewModel = MemoryDatabaseViewModel(application)
        cameraViewModel = CameraViewModel(application)

        setContent {
            mapViewModel = MapViewModel(application, getMap(context = applicationContext))
            SouvenirAppTheme {
                MainScreen(mapViewModel, memoryDatabaseViewModel, cameraViewModel)
//                val memories = createSampleMemories()
//                ShowMemories(memoriesList = memories, applicationContext)
            }
        }
    }
}