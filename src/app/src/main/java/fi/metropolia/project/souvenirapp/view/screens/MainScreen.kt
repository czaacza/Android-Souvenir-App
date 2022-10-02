package fi.metropolia.project.souvenirapp.view

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import fi.metropolia.project.souvenirapp.view.components.BottomBar
import fi.metropolia.project.souvenirapp.view.navigation.BottomBarNavigation
import fi.metropolia.project.souvenirapp.viewmodel.MapViewModel
import fi.metropolia.project.souvenirapp.viewmodel.MemoryViewModel

@Composable
fun MainScreen(mapViewModel : MapViewModel, memoryViewModel : MemoryViewModel) {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomBar(navController = navController)
        }) {
        BottomBarNavigation(navController = navController, mapViewModel,memoryViewModel)
    }
}