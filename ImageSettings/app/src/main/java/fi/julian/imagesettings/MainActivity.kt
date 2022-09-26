package fi.julian.imagesettings

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import fi.julian.imagesettings.ui.theme.ImageSettingsTheme
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ImageSettingsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    showMainView()
                }
            }
        }
    }

    @Composable
    fun showMainView(){
        var txtTitle = remember { mutableStateOf("")}
        var txtDescription = remember { mutableStateOf("")}
        var txtLocation = remember { mutableStateOf("")}
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
            Row(){
                Button(onClick = { /*TODO*/ }) {
                    Text(stringResource(R.string.cancel))
                }
                Button(onClick = { /*TODO*/ }) {
                    Text(stringResource(R.string.done))
                }
            }
        }
    }
}

