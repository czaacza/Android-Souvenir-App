package fi.metropolia.project.souvenirapp.view.activities

import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.res.painterResource
import androidx.core.content.res.ResourcesCompat
import androidx.preference.PreferenceManager
import fi.metropolia.project.souvenirapp.R
import fi.metropolia.project.souvenirapp.model.logMemories
import fi.metropolia.project.souvenirapp.view.screens.getMap
import fi.metropolia.project.souvenirapp.view.theme.SouvenirAppTheme
import fi.metropolia.project.souvenirapp.model.trysensor
import fi.metropolia.project.souvenirapp.view.MainScreen
import fi.metropolia.project.souvenirapp.viewmodel.*
import org.osmdroid.config.Configuration

@ExperimentalAnimationApi
class MainActivity : ComponentActivity() {

    private lateinit var mapViewModel: MapViewModel
    private lateinit var memoryDatabaseViewModel: MemoryDatabaseViewModel
    private lateinit var cameraViewModel: CameraViewModel

    private lateinit var sensorViewModel: LightSensorViewModel
    private lateinit var sensorManager: SensorManager
    private lateinit var locationViewModel: LocationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        memoryDatabaseViewModel = MemoryDatabaseViewModel(application)
        cameraViewModel = CameraViewModel(application)
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        sensorViewModel = LightSensorViewModel(sensorManager)
        locationViewModel = LocationViewModel(application, this)


        memoryDatabaseViewModel.clear()

        setContent {
            mapViewModel = MapViewModel(application, getMap(context = this), locationViewModel)

            SouvenirAppTheme {
                MainScreen(
                    mapViewModel,
                    memoryDatabaseViewModel,
                    cameraViewModel,
                    sensorViewModel,
                    locationViewModel,
                )
                trysensor(sensorManager)
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
