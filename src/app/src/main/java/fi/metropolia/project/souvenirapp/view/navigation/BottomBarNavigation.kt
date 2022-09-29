package fi.metropolia.project.souvenirapp.view.navigation


import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import fi.metropolia.project.souvenirapp.view.components.BottomBarScreen
import fi.metropolia.project.souvenirapp.view.screens.CreateScreen
import fi.metropolia.project.souvenirapp.view.screens.ListScreen
import fi.metropolia.project.souvenirapp.view.screens.MapScreen

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