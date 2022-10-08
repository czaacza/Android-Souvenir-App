package fi.metropolia.project.souvenirapp.view.screens

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import fi.metropolia.project.souvenirapp.R
import fi.metropolia.project.souvenirapp.view.theme.LightBlueTint
import fi.metropolia.project.souvenirapp.viewmodel.MapViewModel
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.views.MapView


@Composable
fun MapScreen(
    mapViewModel: MapViewModel
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopAppBar(
            modifier = Modifier
                .fillMaxWidth(),
            backgroundColor = LightBlueTint
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "MAP",
                    style = MaterialTheme.typography.h1,
                    color = MaterialTheme.colors.secondary,
                )
            }
        }
        Box(modifier = Modifier.fillMaxHeight()) {
            AndroidView({ mapViewModel.map })
        }
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