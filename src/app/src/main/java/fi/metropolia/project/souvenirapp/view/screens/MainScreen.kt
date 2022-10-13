package fi.metropolia.project.souvenirapp.view

import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import fi.metropolia.project.souvenirapp.view.components.BottomBar
import fi.metropolia.project.souvenirapp.view.components.BottomBarScreen
import fi.metropolia.project.souvenirapp.view.navigation.BottomBarNavigation
import fi.metropolia.project.souvenirapp.view.theme.MainColor
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
        topBar = {
            val currentStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = currentStackEntry?.destination
            var topAppText = ""

            when (currentDestination?.route) {
                BottomBarScreen.MapScreen.route -> {
                    topAppText = "MAP"
                }
                BottomBarScreen.ListScreen.route -> {
                    topAppText = "MY MEMORIES"
                }
                BottomBarScreen.CreateMemoryScreen.route -> {
                    topAppText = "NEW MEMORY"
                }
                "details" -> {
                    topAppText = "MEMORY DETAILS"
                }
            }

            TopAppBar(
                modifier = Modifier
                    .fillMaxWidth(),
                backgroundColor = MainColor
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = topAppText,
                        style = MaterialTheme.typography.h1,
                        color = MaterialTheme.colors.primary,
                    )
                }
            }
        },
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