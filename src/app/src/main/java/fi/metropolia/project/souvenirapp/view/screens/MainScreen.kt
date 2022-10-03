package fi.metropolia.project.souvenirapp.view

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import fi.metropolia.project.souvenirapp.view.components.BottomBar
import fi.metropolia.project.souvenirapp.view.navigation.BottomBarNavigation
import fi.metropolia.project.souvenirapp.viewmodel.MapViewModel
import fi.metropolia.project.souvenirapp.viewmodel.MemoryDatabaseViewModel

@Composable
fun MainScreen(mapViewModel: MapViewModel, memoryDatabaseViewModel: MemoryDatabaseViewModel) {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomBar(navController = navController)
        }) {
        BottomBarNavigation(navController = navController, mapViewModel, memoryDatabaseViewModel)
    }
}