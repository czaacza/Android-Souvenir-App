package fi.metropolia.project.souvenirapp.view.activities

import android.content.Context
import android.graphics.BitmapFactory
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.ui.graphics.asImageBitmap
import androidx.preference.PreferenceManager
import fi.metropolia.project.souvenirapp.view.MainScreen
import fi.metropolia.project.souvenirapp.view.screens.getMap
import fi.metropolia.project.souvenirapp.view.theme.SouvenirAppTheme
import fi.metropolia.project.souvenirapp.viewmodel.CameraViewModel
import fi.metropolia.project.souvenirapp.model.trysensor
import fi.metropolia.project.souvenirapp.viewmodel.LightSensorViewModel
import fi.metropolia.project.souvenirapp.viewmodel.MapViewModel
import fi.metropolia.project.souvenirapp.viewmodel.MemoryDatabaseViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.osmdroid.config.Configuration

class MainActivity : ComponentActivity() {

    private lateinit var mapViewModel: MapViewModel
    private lateinit var memoryDatabaseViewModel: MemoryDatabaseViewModel
    private lateinit var cameraViewModel: CameraViewModel

    private lateinit var sensorViewModel: LightSensorViewModel
    private lateinit var sensorManager: SensorManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Configuration.getInstance()
            .load(application, PreferenceManager.getDefaultSharedPreferences(application))

        memoryDatabaseViewModel = MemoryDatabaseViewModel(application)
        cameraViewModel = CameraViewModel(application)
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensorViewModel = LightSensorViewModel(sensorManager)
//        memoryDatabaseViewModel.clear()

        setContent {
            mapViewModel = MapViewModel(application, getMap(context = applicationContext))
            SouvenirAppTheme {
                MainScreen(mapViewModel, memoryDatabaseViewModel, cameraViewModel, sensorViewModel)

                trysensor(sensorManager)

                setContent {
                    mapViewModel = MapViewModel(application, getMap(context = applicationContext))
                    SouvenirAppTheme {
                        MainScreen(
                            mapViewModel,
                            memoryDatabaseViewModel,
                            cameraViewModel,
                            sensorViewModel
                        )
                    }
                }
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
