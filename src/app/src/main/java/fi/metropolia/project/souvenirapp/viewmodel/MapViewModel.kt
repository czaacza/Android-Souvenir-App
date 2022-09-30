package fi.metropolia.project.souvenirapp.viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.AndroidViewModel
import androidx.preference.PreferenceManager
import fi.metropolia.project.souvenirapp.R
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView

class MapViewModel(application: Application, val map: MapView) : AndroidViewModel(application) {
    var isInitialized = false

    init{
        Configuration.getInstance().load(application, PreferenceManager.getDefaultSharedPreferences(application))

        if(!isInitialized){
            map.setTileSource(TileSourceFactory.MAPNIK)
            map.setMultiTouchControls(true)
            map.controller.setZoom(9.0)
            map.controller.setCenter(GeoPoint(100.0,100.0))
            isInitialized = true
        }
    }

}