package fi.metropolia.project.souvenirapp.view.screens

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.gson.Gson
import fi.metropolia.project.souvenirapp.R
import fi.metropolia.project.souvenirapp.model.data.Memory
import fi.metropolia.project.souvenirapp.view.components.BottomBarScreen
import fi.metropolia.project.souvenirapp.viewmodel.MemoryDatabaseViewModel

@Composable
fun DetailsScreen(
    memoryId: String?,
    navController: NavController,
    memoryDatabaseViewModel: MemoryDatabaseViewModel
) {
    val memories = memoryDatabaseViewModel.memories.observeAsState()
    var chosenMemory: Memory? = null
    var isMemoryFound = remember {
        mutableStateOf(false)
    }
    if (memoryId != null && !isMemoryFound.value) {
        if (memories.value != null) {
            chosenMemory = memories.value!!.find { memory ->
                memory.id.toString() == memoryId
            }
        }
        isMemoryFound.value = true
    }
    if (chosenMemory == null) {
        return
    }
    Box() {
        Icon(
            painter = painterResource(id = R.drawable.ic_back),
            contentDescription = "Go Back",
            Modifier
                .clickable {
                    navController.navigate(route = BottomBarScreen.ListScreen.route)
                }
        )
    }
    Column(
        Modifier
            .fillMaxSize()
            .padding(10.dp, 10.dp, 10.dp, 65.dp),
    ) {

        Text(
            text = "TITLE",
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp),
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        )
        /*IMAGE*/

        Card() {
            Text(text = "DESCRIPTION")
        }
        Card() {
            Text(text = "LIGHT,LOC,DATE")
        }
    }

}