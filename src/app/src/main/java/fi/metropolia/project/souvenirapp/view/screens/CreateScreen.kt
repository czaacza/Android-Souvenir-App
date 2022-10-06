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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import fi.metropolia.project.souvenirapp.R
import fi.metropolia.project.souvenirapp.view.components.BottomBarScreen
import fi.metropolia.project.souvenirapp.viewmodel.CameraViewModel
import fi.metropolia.project.souvenirapp.viewmodel.LightSensorViewModel
import fi.metropolia.project.souvenirapp.viewmodel.MemoryDatabaseViewModel
import kotlin.math.floor


@Composable
fun CreateScreen(
    memoryDatabaseViewModel: MemoryDatabaseViewModel,
    cameraViewModel: CameraViewModel,
    sensorViewModel: LightSensorViewModel,
    navController: NavController
) {
    val txtTitle = remember { mutableStateOf("") }
    val txtDescription = remember { mutableStateOf("") }
    val txtLocation = remember { mutableStateOf("") }

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
            } else if(bitmap != null && bitmap.value != null){
                Image(bitmap = bitmap.value!!.asImageBitmap() , contentDescription = "Camera picture")
            }
        }
        Spacer(modifier = Modifier.fillMaxHeight(0.05f))
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
                textStyle = TextStyle.Default.copy(fontSize = 22.sp),
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
                modifier = Modifier
                    .fillMaxWidth(),
                onValueChange = { txtLocation.value = it }
            )

            Spacer(modifier = Modifier.fillMaxHeight(0.3f))

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
                modifier = Modifier,
                onClick = {
                    memoryDatabaseViewModel.createNewMemory(
                        txtTitle.value,
                        txtDescription.value,
                        0.0,
                        0.0,
                        cameraViewModel.imageAbsolutePath
                    )
                    navController.navigate(route = BottomBarScreen.ListScreen.route)
                })
        }

    }
}