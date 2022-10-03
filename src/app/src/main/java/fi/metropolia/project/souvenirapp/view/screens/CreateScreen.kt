package fi.metropolia.project.souvenirapp.view.screens

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import fi.metropolia.project.souvenirapp.R
import fi.metropolia.project.souvenirapp.model.getBitmapFromSampleFile
import fi.metropolia.project.souvenirapp.viewmodel.MemoryDatabaseViewModel


@Composable
fun CreateScreen(memoryDatabaseViewModel: MemoryDatabaseViewModel) {
    val txtTitle = remember { mutableStateOf("") }
    val txtDescription = remember { mutableStateOf("") }
    val txtLocation = remember { mutableStateOf("") }

    val bitmap: Bitmap? = getBitmapFromSampleFile()

    Column(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.fillMaxWidth()) {
            if (bitmap != null) {
                Image(
                    bitmap = bitmap.asImageBitmap(),
                    contentDescription = "strawberries",
                    modifier = Modifier.align(Alignment.CenterHorizontally)
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
                TextField(txtLocation.value, onValueChange = { txtLocation.value = it })
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