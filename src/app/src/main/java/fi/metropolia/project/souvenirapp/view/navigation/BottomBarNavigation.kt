package fi.metropolia.project.souvenirapp.view.navigation


import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import fi.metropolia.project.souvenirapp.view.components.BottomBarScreen
import fi.metropolia.project.souvenirapp.view.screens.CreateScreen
import fi.metropolia.project.souvenirapp.view.screens.DetailsScreen
import fi.metropolia.project.souvenirapp.view.screens.ListScreen
import fi.metropolia.project.souvenirapp.view.screens.MapScreen
import fi.metropolia.project.souvenirapp.viewmodel.*
import kotlin.math.log

const val SLIDE_DURATION_MS = 300
const val FADE_DURATION_MS = 200

@ExperimentalAnimationApi
@Composable
fun BottomBarNavigation(
    navController: NavHostController,
    mapViewModel: MapViewModel,
    memoryDatabaseViewModel: MemoryDatabaseViewModel,
    cameraViewModel: CameraViewModel,
    sensorViewModel: LightSensorViewModel,
    locationViewModel: LocationViewModel,
    navigationViewModel: NavigationViewModel
) {
    val currentStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentStackEntry?.destination
    val lastDestinationRoute = navigationViewModel.lastDestinationRoute.observeAsState()

    if (currentDestination?.route != "list") {
        navigationViewModel.setLastDestination()
    }

    AnimatedNavHost(
        navController = navController,
        startDestination = BottomBarScreen.ListScreen.route,
    ) {
        composable(
            route = BottomBarScreen.MapScreen.route,
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { fullWidth -> -2 * fullWidth },
                    animationSpec = tween(SLIDE_DURATION_MS)
                )
            },
            exitTransition = {
                fadeOut(animationSpec = tween(FADE_DURATION_MS))
            }
        ) {
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
        composable(
            route = BottomBarScreen.ListScreen.route,
            enterTransition = {
                if (lastDestinationRoute.value == BottomBarScreen.MapScreen.route) {
                    slideInHorizontally(
                        initialOffsetX = { fullWidth -> 2 * fullWidth },
                        animationSpec = tween(SLIDE_DURATION_MS)
                    )
                } else if (lastDestinationRoute.value == BottomBarScreen.CreateMemoryScreen.route) {
                    slideInHorizontally(
                        initialOffsetX = { fullWidth -> -2 * fullWidth },
                        animationSpec = tween(SLIDE_DURATION_MS)
                    )
                } else {
                    slideInVertically(
                        initialOffsetY = { fullHeight -> -2 * fullHeight },
                        animationSpec = tween(SLIDE_DURATION_MS)
                    )
                }
            },
            exitTransition = {
                if (currentDestination?.route == BottomBarScreen.MapScreen.route) {
                    slideOutHorizontally(
                        targetOffsetX = { fullWidth -> 2 * fullWidth },
                        animationSpec = tween(SLIDE_DURATION_MS)
                    )
                } else if (currentDestination?.route == BottomBarScreen.CreateMemoryScreen.route) {
                    slideOutHorizontally(
                        targetOffsetX = { fullWidth -> -2 * fullWidth },
                        animationSpec = tween(SLIDE_DURATION_MS)
                    )
                } else {
                    fadeOut(animationSpec = tween(FADE_DURATION_MS))
                }
            },
        ) {
            ListScreen(memoryDatabaseViewModel, navController)
        }
        composable(
            route = BottomBarScreen.CreateMemoryScreen.route,
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { fullWidth -> 2 * fullWidth },
                    animationSpec = tween(SLIDE_DURATION_MS)
                )
            },
            exitTransition = {
                fadeOut(animationSpec = tween(FADE_DURATION_MS))
            }
        ) {
            locationViewModel.startLocationTracking()
            CreateScreen(
                memoryDatabaseViewModel,
                cameraViewModel,
                sensorViewModel,
                locationViewModel,
                navController
            )
        }
        composable(
            route = "details/{memory}",
            enterTransition = {
                slideInVertically(
                    initialOffsetY = { fullHeight -> 2 * fullHeight },
                    animationSpec = tween(SLIDE_DURATION_MS)
                )
            }
        ) { navBackStackEntry ->
            DetailsScreen(
                navBackStackEntry.arguments?.getString("memory"),
                navController,
                memoryDatabaseViewModel
            )
        }
    }
}