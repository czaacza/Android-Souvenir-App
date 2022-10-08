package fi.metropolia.project.souvenirapp.view.navigation


import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import fi.metropolia.project.souvenirapp.model.location.LocationManager
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
            mapViewModel.setMap()
            mapViewModel.initialize()
            MapScreen(mapViewModel)
        }
        composable(BottomBarScreen.ListScreen.route) {
            ListScreen(memoryDatabaseViewModel)
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