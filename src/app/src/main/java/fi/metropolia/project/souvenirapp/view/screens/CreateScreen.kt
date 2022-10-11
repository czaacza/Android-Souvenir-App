package fi.metropolia.project.souvenirapp.view.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
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
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import fi.metropolia.project.souvenirapp.R
import fi.metropolia.project.souvenirapp.view.components.BottomBarScreen
import fi.metropolia.project.souvenirapp.view.theme.MainColorVariant
import fi.metropolia.project.souvenirapp.viewmodel.CameraViewModel
import fi.metropolia.project.souvenirapp.viewmodel.LightSensorViewModel
import fi.metropolia.project.souvenirapp.viewmodel.LocationViewModel
import fi.metropolia.project.souvenirapp.viewmodel.MemoryDatabaseViewModel


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


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.9f),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.4f)
                    .padding(10.dp, 10.dp, 10.dp, 0.dp)
                    .clip(shape = MaterialTheme.shapes.large)
                    .selectable(true, onClick = {
                        if (!isPictureTaken.value) {
                            cameraViewModel.launch()
                            isPictureTaken.value = true
                        }
                    }),
            ) {
                if (!isPictureTaken.value) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colors.surface),
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
                        contentDescription = "Camera picture",
                        modifier = Modifier.fillMaxSize()
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
                if (txtLight.value.isEmpty()) {
                    txtLight.value = "0.0"
                }
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
                backgroundColor = MainColorVariant,
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
                        bitmap.value!!,
                    )
                    navController.navigate(route = BottomBarScreen.ListScreen.route)
                })
        }
    }
}