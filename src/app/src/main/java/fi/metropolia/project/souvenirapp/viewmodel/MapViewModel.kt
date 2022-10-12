package fi.metropolia.project.souvenirapp.viewmodel

import android.app.Application
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.preference.PreferenceManager
import fi.metropolia.project.souvenirapp.model.data.Memory
import fi.metropolia.project.souvenirapp.view.screens.getMap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.CustomZoomButtonsController
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

class MapViewModel(
    application: Application,
    var map: MapView,
    val locationViewModel: LocationViewModel
) : AndroidViewModel(application) {

    private val app = application

    init {
        Configuration.getInstance()
            .load(app, PreferenceManager.getDefaultSharedPreferences(app))
        map.setTileSource(TileSourceFactory.MAPNIK)
    }

    fun initialize() {
        map.controller.setZoom(9.0)
        map.setMultiTouchControls(true)
        map.zoomController.setVisibility(CustomZoomButtonsController.Visibility.NEVER)
    }

    fun centerMap(centrePoint: GeoPoint) {
        map.controller.setCenter(centrePoint)
    }

    fun setMarkers(memories: List<Memory>) {
        viewModelScope.launch(Dispatchers.IO) {
            memories.forEach { memory ->
                if(memory.location == ""){
                    return@forEach
                }
                val marker = Marker(map)
                marker.position = locationViewModel.getGeoPoint(memory.location)
                marker.title = memory.title
                marker.subDescription = memory.location

                marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
                map.overlays.add(marker)
            }
        }
    }

    @Composable
    fun setMap() {
        map = getMap(app)
    }
}

