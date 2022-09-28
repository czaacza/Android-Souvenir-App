package fi.metropolia.project.souvenirapp.ui

import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigation
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import fi.metropolia.project.souvenirapp.ui.components.BottomBar
import fi.metropolia.project.souvenirapp.ui.navigation.BottomBarNavigation

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomBar(navController = navController)
        }) {
        BottomBarNavigation(navController = navController)
    }
}