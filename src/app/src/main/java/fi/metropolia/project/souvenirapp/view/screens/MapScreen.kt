package fi.metropolia.project.souvenirapp.view.screens

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import fi.metropolia.project.souvenirapp.R
import fi.metropolia.project.souvenirapp.viewmodel.LocationViewModel
import fi.metropolia.project.souvenirapp.viewmodel.MapViewModel
import fi.metropolia.project.souvenirapp.viewmodel.MemoryDatabaseViewModel
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.views.MapView


@Composable
fun MapScreen(
    mapViewModel: MapViewModel,
    locationViewModel: LocationViewModel,
    memoryDatabaseViewModel: MemoryDatabaseViewModel
) {
    val isMapInitialized = remember { mutableStateOf(false) }
    val isMapCentered = remember { mutableStateOf(false) }
    val areMarkersSet = remember { mutableStateOf(false) }

//   INITIALIZE THE MAP
    if (!isMapInitialized.value) {
        mapViewModel.setMap()
        mapViewModel.initialize()
        isMapInitialized.value = true
    }

    // CENTRE THE MAP
    val currentlocationPoint = locationViewModel.locationPoint.observeAsState()
    locationViewModel.startLocationTracking()

    if (currentlocationPoint.value != null && !isMapCentered.value
    ) {
        mapViewModel.centerMap(currentlocationPoint.value!!)
        isMapCentered.value = true
        locationViewModel.stopLocationTracking()
    }

//  SET MARKERS
    val memories = memoryDatabaseViewModel.memories.observeAsState()

    if (memories.value != null && !areMarkersSet.value) {
        mapViewModel.setMarkers(memories.value!!)
        areMarkersSet.value = true
    }

    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView({ mapViewModel.map })
    }

}

@Composable
fun getMap(context: Context): MapView {
    val map = remember {
        MapView(context).apply {
            id = R.id.map
        }
    }
    return map
}