package fi.metropolia.project.souvenirapp.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fi.metropolia.project.souvenirapp.R


@Composable
fun CreateScreen() {
    var txtTitle = remember { mutableStateOf("") }
    var txtDescription = remember { mutableStateOf("") }
    var txtLocation = remember { mutableStateOf("") }
    Column(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.fillMaxWidth()) {
            //Image()
            Text("Here is supposed to be the Image", modifier = Modifier.align(Alignment.CenterHorizontally))
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
                TextField(txtTitle.value, onValueChange = {txtTitle.value = it})
                Spacer(modifier = Modifier.size(20.dp))
                TextField(txtDescription.value, onValueChange = {txtDescription.value = it})
                Spacer(modifier = Modifier.size(20.dp))
                TextField(txtLocation.value, onValueChange = {txtLocation.value = it})
            }
        }
        Row(modifier = Modifier.fillMaxWidth()){
            Spacer(modifier = Modifier.size(220.dp))
            Row(){
                Button(onClick = { /*TODO*/ }) {
                    Text(stringResource(R.string.cancel))
                }
                Spacer(modifier = Modifier.size(10.dp))
                Button(onClick = { /*TODO*/ }) {
                    Text(stringResource(R.string.done))
                }
            }
        }
    }
}