package fi.metropolia.project.souvenirapp.ui.screens

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
fun CreateScreen() {
    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color.Green)
        .padding(50.dp)
    )
    {

    }
    Text("CREATE MEMORY", fontSize = 48.sp)
}