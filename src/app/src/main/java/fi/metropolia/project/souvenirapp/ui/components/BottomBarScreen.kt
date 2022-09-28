package fi.metropolia.project.souvenirapp.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarScreen(
    val route : String,
    val title : String,
    val icon : ImageVector
){
    object MapScreen : BottomBarScreen(
        route = "map",
        title = "Map",
        icon = Icons.Default.LocationOn
    )

    object ListScreen : BottomBarScreen(
        route = "list",
        title = "Memories",
        icon = Icons.Default.List
    )

    object CreateMemoryScreen : BottomBarScreen(
        route = "create",
        title = "Create",
        icon = Icons.Default.Add
    )
}
