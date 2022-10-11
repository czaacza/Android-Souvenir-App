package fi.metropolia.project.souvenirapp.view.screens

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import fi.metropolia.project.souvenirapp.R
import fi.metropolia.project.souvenirapp.model.data.MemoryEntity
import fi.metropolia.project.souvenirapp.view.components.BottomBarScreen
import fi.metropolia.project.souvenirapp.view.theme.MainColorVariant
import fi.metropolia.project.souvenirapp.viewmodel.MemoryDatabaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Composable
fun ListScreen(
    memoryDatabaseViewModel: MemoryDatabaseViewModel,
    navController: NavController
) {
    val memories = memoryDatabaseViewModel.memories.observeAsState()

//    logMemories(memoryDatabaseViewModel)

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(modifier = Modifier.fillMaxHeight(0.93f)) {
            if (memories != null && memories.value != null) {
                items(memories.value!!) { memory ->
                    ShowMemoryCard(memory = memory)
                }
            }

            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp)),
                        colors = ButtonDefaults.buttonColors(backgroundColor = MainColorVariant),
                        onClick = {
                            navController.navigate(BottomBarScreen.CreateMemoryScreen.route)
                        }
                    ) {
                        Icon(
                            Icons.Outlined.Add,
                            tint = MaterialTheme.colors.primary,
                            contentDescription = "Add icon"
                        )
                        Text(
                            text = "Create new memory",
                            color = MaterialTheme.colors.primary,
                            style = MaterialTheme.typography.body1
                        )
                    }
                }
            }
        }

    }


}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun ShowMemoryCard(memory: MemoryEntity) {
    val coroutineScope = rememberCoroutineScope()
    var bitmap = remember { mutableStateOf<ImageBitmap?>(null) }
    coroutineScope.launch(Dispatchers.Default) {
        bitmap.value = BitmapFactory.decodeFile(memory.imageUri).asImageBitmap()
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp, 20.dp, 20.dp, 0.dp)
            .clip(RoundedCornerShape(10.dp)),
        elevation = 20.dp
    ) {
        Column(modifier = Modifier.padding(5.dp)) {
            Text(
                text = memory.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp),
                style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            )
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.4F)
                        .clip(RoundedCornerShape(10.dp))
                )
                {
                    if (bitmap.value != null) {
                        Image(
                            bitmap = bitmap.value!!,
                            contentDescription = "memory image",
                        )
                    } else {
                        Row(
                            modifier = Modifier.fillMaxSize(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 10.dp)
                ) {
                    Row(Modifier.fillMaxWidth()) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_explore),
                            contentDescription = null,
                            Modifier
                                .size(25.dp, 25.dp)
                                .padding(top = 2.dp, end = 4.dp)
                        )
                        Text(
                            text = "${memory.location} ",
                            modifier = Modifier.padding(bottom = 3.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(5.dp))
                    Row(Modifier.fillMaxWidth()) {
                        Image(
                            painterResource(R.drawable.ic_calendar), contentDescription = null,
                            Modifier
                                .size(25.dp, 25.dp)
                                .padding(bottom = 4.dp, end = 3.dp)
                        )
                        Text(text = " ${memory.date} ", modifier = Modifier.padding(top = 3.dp))
                    }
                    Spacer(modifier = Modifier.height(5.dp))
                    Row(Modifier.fillMaxWidth()) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_light),
                            contentDescription = null,
                            Modifier
                                .size(25.dp, 25.dp)
                                .padding(bottom = 3.dp)
                        )
                        Text(text = " ${memory.light} ", modifier = Modifier.padding(top = 3.dp))
                    }
                }
            }
        }

    }
}
