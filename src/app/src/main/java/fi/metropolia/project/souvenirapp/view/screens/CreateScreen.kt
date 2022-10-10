package fi.metropolia.project.souvenirapp.view.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import fi.metropolia.project.souvenirapp.R
import fi.metropolia.project.souvenirapp.view.components.BottomBarScreen
import fi.metropolia.project.souvenirapp.view.theme.LightBlueTint
import fi.metropolia.project.souvenirapp.viewmodel.CameraViewModel
import fi.metropolia.project.souvenirapp.viewmodel.LightSensorViewModel
import fi.metropolia.project.souvenirapp.viewmodel.LocationViewModel
import fi.metropolia.project.souvenirapp.viewmodel.MemoryDatabaseViewModel
import fi.metropolia.project.souvenirapp.view.activities.MainActivity


@SuppressLint("MissingPermission")
@Composable
fun CreateScreen(
    memoryDatabaseViewModel: MemoryDatabaseViewModel,
    cameraViewModel: CameraViewModel,
    sensorViewModel: LightSensorViewModel,
    locationViewModel: LocationViewModel,
    navController: NavController
) {
    val locationPoint = locationViewModel.locationPoint.observeAsState()

    val txtTitle = remember { mutableStateOf("") }
    val txtDescription = remember { mutableStateOf("") }
    val txtLocation = remember {
        mutableStateOf(
            ""
        )
    }
    val txtLight = remember { mutableStateOf("") }
    val lightState = sensorViewModel.sunData.observeAsState()

    if (locationPoint.value != null) {
        txtLocation.value = locationViewModel.getAddress(locationPoint.value!!)
    }
    if (lightState.value != null) {
        txtLight.value = lightState.value.toString()
    }

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

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
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
                    text = "NEW MEMORY",
                    style = MaterialTheme.typography.h1,
                    color = MaterialTheme.colors.secondary,
                )
            }
        }

        Column(
            modifier = Modifier.fillMaxWidth().fillMaxHeight(0.9f),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.4f)
                    .padding(10.dp, 10.dp, 10.dp, 0.dp)
                    .clip(shape = MaterialTheme.shapes.large)
                    .background(MaterialTheme.colors.surface)
                    .selectable(true, onClick = {
                        if (!isPictureTaken.value) {
                            cameraViewModel.launch()
                            isPictureTaken.value = true
                        }
                    }),
            ) {
                if (!isPictureTaken.value) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = stringResource(id = R.string.add_picture),
                            style = MaterialTheme.typography.subtitle2
                        )
                    }
                } else if (bitmap != null && bitmap.value != null) {
                    Image(
                        bitmap = bitmap.value!!.asImageBitmap(),
                        contentDescription = "Camera picture"
                    )
                }
            }
//        Spacer(modifier = Modifier.fillMaxHeight(0.05f))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    value = txtTitle.value,
                    label = {
                        Text(text = stringResource(id = R.string.title))
                    },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    onValueChange = { txtTitle.value = it }
                )
                OutlinedTextField(
                    value = txtDescription.value,
                    label = {
                        Text(text = stringResource(id = R.string.description))
                    },
                    singleLine = false,
                    modifier = Modifier
                        .fillMaxHeight(0.3f)
                        .fillMaxWidth(),
                    onValueChange = { txtDescription.value = it }
                )
                OutlinedTextField(
                    value = txtLocation.value,
                    label = {
                        Text(text = stringResource(id = R.string.location))
                    },
                    enabled = false,
                    modifier = Modifier
                        .fillMaxWidth(),
                    onValueChange = { txtLocation.value = it }
                )
                OutlinedTextField(
                    value = txtLight.value,
                    label = {
                        Text(text = "Light")
                    },
                    enabled = false,
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    onValueChange = { txtTitle.value = it }
                )

            }

            ExtendedFloatingActionButton(
                backgroundColor = MaterialTheme.colors.secondary,
                icon = {
                    Icon(
                        imageVector = Icons.Filled.Done,
                        tint = MaterialTheme.colors.primary,
                        contentDescription = "Done icon"
                    )
                },
                text = {
                    Text(text = "Done", color = MaterialTheme.colors.primary)
                },
                modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = {
                    memoryDatabaseViewModel.createNewMemory(
                        txtTitle.value,
                        txtDescription.value,
                        txtLocation.value,
                        txtLight.value.toFloat(),
                        cameraViewModel.imageAbsolutePath
                    )
                    navController.navigate(route = BottomBarScreen.ListScreen.route)
                })
        }
    }
}