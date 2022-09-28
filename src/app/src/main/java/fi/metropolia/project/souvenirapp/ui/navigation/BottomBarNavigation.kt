package fi.metropolia.project.souvenirapp.ui.navigation

import androidx.compose.material.BottomAppBar
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import fi.metropolia.project.souvenirapp.ui.components.BottomBarScreen
import fi.metropolia.project.souvenirapp.ui.screens.CreateScreen
import fi.metropolia.project.souvenirapp.ui.screens.ListScreen
import fi.metropolia.project.souvenirapp.ui.screens.MapScreen

@Composable
fun BottomBarNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.ListScreen.route
    ) {
        composable(BottomBarScreen.MapScreen.route){
            MapScreen()
        }
        composable(BottomBarScreen.ListScreen.route){
            ListScreen()
        }
        composable(BottomBarScreen.CreateMemoryScreen.route){
            CreateScreen()
        }
    }
}