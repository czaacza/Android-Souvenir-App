package fi.metropolia.project.souvenirapp.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MapScreen() {
    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color.Red)
        .padding(50.dp)
    ) {

    }
    Text("MAP", fontSize = 48.sp)
}