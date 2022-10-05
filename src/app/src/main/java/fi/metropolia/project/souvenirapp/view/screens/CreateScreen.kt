package fi.metropolia.project.souvenirapp.view.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import fi.metropolia.project.souvenirapp.R
import fi.metropolia.project.souvenirapp.viewmodel.CameraViewModel
import fi.metropolia.project.souvenirapp.viewmodel.LightSensorViewModel
import fi.metropolia.project.souvenirapp.viewmodel.MemoryDatabaseViewModel
import kotlin.math.floor


@Composable
fun CreateScreen(
    memoryDatabaseViewModel: MemoryDatabaseViewModel,
    cameraViewModel: CameraViewModel,
    sensorViewModel: LightSensorViewModel
) {
    val txtTitle = remember { mutableStateOf("") }
    val txtDescription = remember { mutableStateOf("") }
    val txtLocation = remember { mutableStateOf("") }

    val isPictureTaken = remember {
        mutableStateOf(false)
    }
    val bitmap = cameraViewModel.imageBitmap.observeAsState()
    cameraViewModel.SetLauncher()

    if (!isPictureTaken.value) {
        cameraViewModel.launch()
        isPictureTaken.value = true
        return
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        val sunData = sensorViewModel.sunData.observeAsState()

        Column(modifier = Modifier.fillMaxWidth()) {
            if (bitmap != null && bitmap.value != null) {
                Image(
                    bitmap = bitmap.value!!.asImageBitmap(),
                    contentDescription = "strawberries",
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .rotate(90f)
                        .fillMaxSize(),
                )
            }
            Spacer(modifier = Modifier.size(100.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                Column() {
                    Text(stringResource(R.string.title))
                    Spacer(modifier = Modifier.size(55.dp))
                    Text(stringResource(R.string.description))
                    Spacer(modifier = Modifier.size(53.dp))
                    Text(stringResource(R.string.location))
                }
                Column() {
                    TextField(txtTitle.value, onValueChange = { txtTitle.value = it })
                    Spacer(modifier = Modifier.size(20.dp))
                    TextField(txtDescription.value, onValueChange = { txtDescription.value = it })
                    Spacer(modifier = Modifier.size(20.dp))
                    TextField(txtLocation.value, onValueChange = { txtLocation.value = it })
                }
            }
        }

        Row(modifier = Modifier.fillMaxWidth()) {
            if (sunData.value == null) {
                Text(text = "NO LIGHT SENSOR ON YOUR PHONE")
            } else {
                Text(text = "LIGHT = ${sunData.value}")
            }
        }
        Row(modifier = Modifier.fillMaxWidth()) {
            Spacer(modifier = Modifier.size(220.dp))
            Row() {
                Button(onClick = { /*TODO*/ }) {
                    Text(stringResource(R.string.cancel))
                }
                Spacer(modifier = Modifier.size(10.dp))
                Button(onClick = {
                    if (txtTitle.value.isNotEmpty()
                        && txtDescription.value.isNotEmpty()
                        && txtLocation.value.isNotEmpty()
                    ) {
                        Log.d("DBG", "imgPath: ${cameraViewModel.imageAbsolutePath}")

                        memoryDatabaseViewModel.createNewMemory(
                            txtTitle.value,
                            txtDescription.value,
                            0.0,
                            0.0,
                            cameraViewModel.imageAbsolutePath
                        )
                    }
                }) {
                    Text(stringResource(R.string.done))
                }

            }
        }
    }
}