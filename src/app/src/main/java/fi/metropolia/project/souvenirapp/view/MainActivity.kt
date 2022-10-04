package fi.metropolia.project.souvenirapp.view

import android.content.Context
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.content.ContextCompat
import androidx.preference.PreferenceManager
import fi.metropolia.project.souvenirapp.model.logMemories
import fi.metropolia.project.souvenirapp.model.trysensor
import fi.metropolia.project.souvenirapp.view.screens.getMap
import fi.metropolia.project.souvenirapp.view.theme.SouvenirAppTheme
import fi.metropolia.project.souvenirapp.viewmodel.LightSensorViewModel
import fi.metropolia.project.souvenirapp.viewmodel.MapViewModel
import fi.metropolia.project.souvenirapp.viewmodel.MemoryDatabaseViewModel
import org.osmdroid.config.Configuration

class MainActivity : ComponentActivity() {

    private lateinit var mapViewModel: MapViewModel
    private lateinit var memoryDatabaseViewModel : MemoryDatabaseViewModel

    private lateinit var sensorViewModel: LightSensorViewModel
    private lateinit var sensorManager: SensorManager



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Configuration.getInstance()
            .load(application, PreferenceManager.getDefaultSharedPreferences(application))

        memoryDatabaseViewModel = MemoryDatabaseViewModel(application)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensorViewModel = LightSensorViewModel(sensorManager)
        trysensor(sensorManager)
        
        setContent {
            mapViewModel = MapViewModel(application, getMap(context = applicationContext))
            SouvenirAppTheme {
                MainScreen(mapViewModel, memoryDatabaseViewModel,sensorViewModel)
//                val memories = createSampleMemories()
//                ShowMemories(memoriesList = memories, applicationContext)
            }
        }
    }

    override fun onResume() {
        super.onResume()

        sensorManager.registerListener(
            sensorViewModel.sensorEventListener,
            sensorViewModel.sunSensor,
            SensorManager.SENSOR_DELAY_UI
        )
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(sensorViewModel.sensorEventListener)
    }
}