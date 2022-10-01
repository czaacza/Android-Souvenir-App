package fi.metropolia.project.souvenirapp.view

import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.preference.PreferenceManager
import fi.metropolia.project.souvenirapp.view.screens.getMap
import fi.metropolia.project.souvenirapp.view.theme.SouvenirAppTheme
import fi.metropolia.project.souvenirapp.viewmodel.MapViewModel
import org.osmdroid.config.Configuration
import java.io.File

class MainActivity : ComponentActivity() {

    private lateinit var mapViewModel: MapViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Configuration.getInstance()
            .load(application, PreferenceManager.getDefaultSharedPreferences(application))

        setContent {
            mapViewModel = MapViewModel(application, getMap(context = applicationContext))
            SouvenirAppTheme {
                MainScreen(mapViewModel)
//                val memories = createSampleMemories()
//                ShowMemories(memoriesList = memories, applicationContext)
            }
        }
    }
}