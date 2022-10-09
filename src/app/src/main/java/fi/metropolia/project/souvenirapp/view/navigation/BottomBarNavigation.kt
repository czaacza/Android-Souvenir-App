package fi.metropolia.project.souvenirapp.view.navigation


import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import fi.metropolia.project.souvenirapp.view.components.BottomBarScreen
import fi.metropolia.project.souvenirapp.view.screens.CreateScreen
import fi.metropolia.project.souvenirapp.view.screens.ListScreen
import fi.metropolia.project.souvenirapp.view.screens.MapScreen
import fi.metropolia.project.souvenirapp.viewmodel.*

@Composable
fun BottomBarNavigation(
    navController: NavHostController,
    mapViewModel: MapViewModel,
    memoryDatabaseViewModel: MemoryDatabaseViewModel,
    cameraViewModel: CameraViewModel,
    sensorViewModel: LightSensorViewModel,
    locationViewModel: LocationViewModel
) {
    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.ListScreen.route
    ) {
        composable(BottomBarScreen.MapScreen.route) {
            val isMapInitialized = remember { mutableStateOf(false) }
            val isMapCentered = remember { mutableStateOf(false) }
            val areMarkersSet = remember { mutableStateOf(false) }

//          INITIALIZE THE MAP
            if (!isMapInitialized.value) {
                mapViewModel.setMap()
                mapViewModel.initialize()
                isMapInitialized.value = true
            }

//          CENTRE THE MAP
            val currentlocationPoint = locationViewModel.locationPoint.observeAsState()

            locationViewModel.startLocationTracking()
            if (currentlocationPoint.value != null && !isMapCentered.value
            ) {
                mapViewModel.centerMap(currentlocationPoint.value!!)
                isMapCentered.value = true
                locationViewModel.stopLocationTracking()
            }

//          SET MARKERS
            val memories = memoryDatabaseViewModel.memories.observeAsState()

            if (memories.value != null && !areMarkersSet.value) {
                mapViewModel.setMarkers(memories.value!!)
                areMarkersSet.value = true
            }

            MapScreen(mapViewModel)
        }
        composable(BottomBarScreen.ListScreen.route) {
            ListScreen(memoryDatabaseViewModel, navController)
        }
        composable(BottomBarScreen.CreateMemoryScreen.route) {
            locationViewModel.startLocationTracking()
            CreateScreen(
                memoryDatabaseViewModel,
                cameraViewModel,
                sensorViewModel,
                locationViewModel,
                navController
            )
        }
    }
}