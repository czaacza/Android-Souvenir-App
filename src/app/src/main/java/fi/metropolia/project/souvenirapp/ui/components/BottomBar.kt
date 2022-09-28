package fi.metropolia.project.souvenirapp.ui.components

import android.util.Log
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomBar(navController: NavHostController) {
    val screens = listOf(
        BottomBarScreen.MapScreen,
        BottomBarScreen.ListScreen,
        BottomBarScreen.CreateMemoryScreen
    )
    val currentStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentStackEntry?.destination

    BottomNavigation() {
        screens.forEach { screen ->
            BottomNavigationItem(
                label = {
                    Text(text = screen.title)
                },
                icon = {
                    Icon(imageVector = screen.icon, contentDescription = "Icon")
                },
                unselectedContentColor = LocalContentColor.current.copy(ContentAlpha.disabled),
                selected = currentDestination?.hierarchy?.any {
                    it.route == screen.route
                } == true,
                onClick = {
                    Log.d("DBG", "currentDest: ${currentDestination?.hierarchy}")
                    navController.navigate(route = screen.route){
                        popUpTo(navController.graph.findStartDestination().id)
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}