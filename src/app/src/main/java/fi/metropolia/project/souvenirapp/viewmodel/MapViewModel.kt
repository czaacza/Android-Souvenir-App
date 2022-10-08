package fi.metropolia.project.souvenirapp.viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.AndroidViewModel
import androidx.preference.PreferenceManager
import fi.metropolia.project.souvenirapp.R
import fi.metropolia.project.souvenirapp.view.screens.getMap
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView

class MapViewModel(application: Application, var map: MapView) : AndroidViewModel(application) {

    val app = application

    init{
        Configuration.getInstance()
            .load(app, PreferenceManager.getDefaultSharedPreferences(app))
        map.setTileSource(TileSourceFactory.MAPNIK)
    }

    fun initialize() {
        map.controller.setZoom(9.0)
        map.setMultiTouchControls(true)
        map.controller.setCenter(GeoPoint(22.0, 100.0))

    }

    @Composable
    fun setMap(){
        map = getMap(app)
    }
}

