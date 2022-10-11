package fi.metropolia.project.souvenirapp.view.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import fi.metropolia.project.souvenirapp.R

sealed class BottomBarScreen(
    val route : String,
    val title : String,
    val painter : Int
){
    object MapScreen : BottomBarScreen(
        route = "map",
        title = "Map",
        painter = R.drawable.ic_map
    )

    object ListScreen : BottomBarScreen(
        route = "list",
        title = "Memories",
        painter = R.drawable.ic_bookmarks
    )

    object CreateMemoryScreen : BottomBarScreen(
        route = "create",
        title = "Create",
        painter = R.drawable.ic_camera
    )
}
