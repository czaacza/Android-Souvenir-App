package fi.metropolia.project.souvenirapp.view

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import fi.metropolia.project.souvenirapp.view.components.BottomBar
import fi.metropolia.project.souvenirapp.view.navigation.BottomBarNavigation
import fi.metropolia.project.souvenirapp.viewmodel.*

@ExperimentalAnimationApi
@Composable
fun MainScreen(
    mapViewModel: MapViewModel,
    memoryDatabaseViewModel: MemoryDatabaseViewModel,
    cameraViewModel: CameraViewModel,
    sensorViewModel: LightSensorViewModel,
    locationViewModel: LocationViewModel,
) {
    val navController = rememberAnimatedNavController()
    Scaffold(
        bottomBar = {
            BottomBar(navController = navController)
        }) {
        BottomBarNavigation(
            navController,
            mapViewModel,
            memoryDatabaseViewModel,
            cameraViewModel,
            sensorViewModel,
            locationViewModel,
            NavigationViewModel(navController)
        )
    }
}