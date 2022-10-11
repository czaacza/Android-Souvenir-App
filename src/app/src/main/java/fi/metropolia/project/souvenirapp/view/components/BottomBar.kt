package fi.metropolia.project.souvenirapp.view.components

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import fi.metropolia.project.souvenirapp.view.theme.MainColor

@Composable
fun BottomBar(navController: NavHostController) {
    val screens = listOf(
        BottomBarScreen.MapScreen,
        BottomBarScreen.ListScreen,
        BottomBarScreen.CreateMemoryScreen
    )
    val currentStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentStackEntry?.destination

    BottomNavigation(
        backgroundColor = MainColor,
    ) {
        screens.forEach { screen ->
            BottomNavigationItem(
                label = {
                    Text(
                        text = screen.title,
                        color = MaterialTheme.colors.primary,
                        style = MaterialTheme.typography.subtitle1
                    )
                },
                icon = {
                    Icon(
                        imageVector = screen.icon,
                        tint = MaterialTheme.colors.primary,
                        contentDescription = "Icon",
                    )
                },
                unselectedContentColor = LocalContentColor.current.copy(ContentAlpha.disabled),
                selected = currentDestination?.hierarchy?.any {
                    it.route == screen.route
                } == true,
                onClick = {
                    navController.navigate(route = screen.route) {
                        popUpTo(navController.graph.findStartDestination().id)
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}