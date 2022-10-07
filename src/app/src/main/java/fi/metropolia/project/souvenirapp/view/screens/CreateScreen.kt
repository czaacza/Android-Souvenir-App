package fi.metropolia.project.souvenirapp.view.screens

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.PackageManager
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
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import fi.metropolia.project.souvenirapp.R
import fi.metropolia.project.souvenirapp.viewmodel.CameraViewModel
import fi.metropolia.project.souvenirapp.viewmodel.LightSensorViewModel
import fi.metropolia.project.souvenirapp.viewmodel.MemoryDatabaseViewModel
import fi.metropolia.project.souvenirapp.view.activities.MainActivity


@SuppressLint("MissingPermission")
@Composable
fun CreateScreen(
    memoryDatabaseViewModel: MemoryDatabaseViewModel,
    cameraViewModel: CameraViewModel,
    sensorViewModel: LightSensorViewModel
) {
    val txtTitle = remember { mutableStateOf("") }
    val txtDescription = remember { mutableStateOf("") }
    val txtLocation = remember { mutableStateOf("") }

    val cts = CancellationTokenSource()
    var fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(MainActivity())
    /*if (ActivityCompat.checkSelfPermission(LocalContext.current, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
        && ActivityCompat.checkSelfPermission(LocalContext.current, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
    ) {
        Log.d("DBG", "No location access")
        ActivityCompat.requestPermissions(MainActivity(), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION),1)
    }*/
    var location = fusedLocationProviderClient.getCurrentLocation(Priority.PRIORITY_BALANCED_POWER_ACCURACY, cts.token)


    val isPictureTaken = remember {
        mutableStateOf(false)
    }
    val bitmap = cameraViewModel.imageBitmap.observeAsState()
    cameraViewModel.SetLauncher()

    if (!isPictureTaken.value) {
        cameraViewModel.launch()
        isPictureTaken.value = true
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
                        .fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
            Spacer(modifier = Modifier.size(40.dp))
        }
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
                if(location != null) {Text(location.result.toString())}
                else {cts.cancel()}
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
                        memoryDatabaseViewModel.createNewMemory(
                            txtTitle.value,
                            txtDescription.value,
                            0.0,
                            0.0,
                            "uri"
                        )
                    }
                }) {
                    Text(stringResource(R.string.done))
                }

            }
        }
    }
}