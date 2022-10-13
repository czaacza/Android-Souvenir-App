package fi.metropolia.project.souvenirapp.view.screens

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import fi.metropolia.project.souvenirapp.R
import fi.metropolia.project.souvenirapp.model.data.Memory
import fi.metropolia.project.souvenirapp.view.components.BottomBarScreen
import fi.metropolia.project.souvenirapp.viewmodel.MemoryDatabaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun DetailsScreen(
    memoryId: String?,
    navController: NavController,
    memoryDatabaseViewModel: MemoryDatabaseViewModel
) {
    val memories = memoryDatabaseViewModel.memories.observeAsState()
    var chosenMemory = remember {
        mutableStateOf<Memory?>(null)
    }
    val coroutineScope = rememberCoroutineScope()
    var bitmap = remember { mutableStateOf<Bitmap?>(null) }
    var isMemoryFound = remember {
        mutableStateOf(false)
    }
    if (memoryId != null && !isMemoryFound.value) {
        if (memories.value != null) {
            chosenMemory.value = memories.value!!.find { memory ->
                memory.id.toString() == memoryId
            }!!
        }
        isMemoryFound.value = true
    }
    if (chosenMemory.value == null) {
        return
    }

    if (chosenMemory.value!!.imageUri != null) {
        coroutineScope.launch(Dispatchers.Main) {
            bitmap.value = BitmapFactory.decodeFile(chosenMemory.value!!.imageUri)
        }
    }
    Column(
        Modifier
            .fillMaxSize()
            .padding(10.dp, 10.dp, 10.dp, 65.dp),
    ) {
        Text(
            text = chosenMemory.value!!.title,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp, bottom = 10.dp),
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        )
        if (bitmap.value != null) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(.35f)
                    .clip(shape = MaterialTheme.shapes.medium)
            ) {
                Image(
                    bitmap = bitmap.value!!.asImageBitmap(),
                    contentDescription = "memory image",
                    Modifier
                        .fillMaxSize()
                        .clip(shape = MaterialTheme.shapes.medium),
                    alignment = Alignment.Center
                )
            }
        }
        Card(
            Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(top = 20.dp)
        ) {
            Row(
                Modifier
                    .padding(top = 5.dp)
                    .fillMaxWidth(1f)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_description),
                    contentDescription = "description",
                    Modifier
                        .size(35.dp, 35.dp)
                        .padding(top = 7.dp, start = 5.dp)
                )
                Text(
                    text = "Description :",
                    Modifier.padding(start = 5.dp, top = 12.dp),
                    style = TextStyle(fontWeight = FontWeight.Bold)
                )
            }
            Row(Modifier.fillMaxHeight(0.75f), verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = chosenMemory.value!!.description,
                    Modifier.fillMaxWidth(),
                    style = TextStyle(
                        fontSize = 15.sp,
                        textAlign = TextAlign.Center
                    )
                )
            }
        }
        Card(
            Modifier
                .fillMaxWidth()
                .height(70.dp)
                .padding(top = 10.dp)
        ) {
            Row(
                Modifier
                    .padding(top = 5.dp)
                    .fillMaxWidth(1f)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_calendar),
                    contentDescription = "calendar",
                    Modifier
                        .size(35.dp, 35.dp)
                        .padding(top = 7.dp, start = 5.dp)
                )
                Text(
                    text = "Date :",
                    Modifier.padding(start = 5.dp, top = 12.dp),
                    style = TextStyle(fontWeight = FontWeight.Bold)
                )
            }
            Row(Modifier.fillMaxHeight(0.75f), verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = chosenMemory.value!!.date,
                    Modifier.fillMaxWidth(),
                    style = TextStyle(
                        fontSize = 15.sp,
                        textAlign = TextAlign.Center
                    )
                )
            }
        }
        Card(
            Modifier
                .fillMaxWidth()
                .height(70.dp)
                .padding(top = 10.dp)
        ) {
            Row(
                Modifier
                    .padding(top = 5.dp)
                    .fillMaxWidth(1f)
            ) {
                Icon(
                    painter = painterResource(
                        id = R.drawable.ic_light
                    ),
                    contentDescription = "light-bulb",
                    Modifier
                        .size(35.dp, 35.dp)
                        .padding(top = 7.dp, start = 5.dp)
                )
                Text(
                    text = "Light :",
                    Modifier.padding(start = 5.dp, top = 12.dp),
                    style = TextStyle(fontWeight = FontWeight.Bold)
                )
            }
            Row(Modifier.fillMaxHeight(0.75f), verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "${chosenMemory.value!!.light}",
                    Modifier.fillMaxWidth(),
                    style = TextStyle(
                        fontSize = 15.sp,
                        textAlign = TextAlign.Center
                    )
                )
            }
        }
        Card(
            Modifier
                .fillMaxWidth()
                .height(80.dp)
                .padding(top = 10.dp)
        ) {
            Row(
                Modifier
                    .padding(top = 5.dp, start = 5.dp)
                    .fillMaxWidth(1f)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_explore),
                    contentDescription = "compass",
                    Modifier
                        .size(35.dp, 35.dp)
                        .padding(top = 7.dp, start = 5.dp)
                )
                Text(
                    text = "Location :",
                    Modifier.padding(start = 5.dp, top = 12.dp),
                    style = TextStyle(fontWeight = FontWeight.Bold)
                )
            }
            Row(
                Modifier
                    .fillMaxHeight()
                    .padding(start = 120.dp), verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = chosenMemory.value!!.location,
                    Modifier.fillMaxWidth(),
                    style = TextStyle(
                        fontSize = 15.sp,
                        textAlign = TextAlign.Center
                    )
                )
            }
        }

    }
}