package fi.metropolia.project.souvenirapp.view.screens

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import fi.metropolia.project.souvenirapp.R
import fi.metropolia.project.souvenirapp.viewmodel.MapViewModel
import org.osmdroid.views.MapView

@Composable
fun MapScreen(mapViewModel: MapViewModel) {
    Box(modifier = Modifier.fillMaxHeight(0.9f)) {
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