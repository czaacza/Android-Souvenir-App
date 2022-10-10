package fi.metropolia.project.souvenirapp.view.screens

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.size.Size
import fi.metropolia.project.souvenirapp.R
import fi.metropolia.project.souvenirapp.model.data.Memory
import fi.metropolia.project.souvenirapp.view.components.BottomBarScreen
import fi.metropolia.project.souvenirapp.view.theme.LightBlue1
import fi.metropolia.project.souvenirapp.view.theme.LightBlueTint
import fi.metropolia.project.souvenirapp.viewmodel.MemoryDatabaseViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.IOException


@Composable
fun ListScreen(
    memoryDatabaseViewModel: MemoryDatabaseViewModel,
    navController: NavController
) {
    val memories = memoryDatabaseViewModel.memories.observeAsState()

    Column(
        modifier = Modifier.fillMaxSize()
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
                verticalAlignment = CenterVertically
            ) {
                Text(
                    text = "MY MEMORIES",
                    style = MaterialTheme.typography.h1,
                    color = MaterialTheme.colors.secondary,
                )
            }
        }
        Column(Modifier.verticalScroll(rememberScrollState())) {
            if (memories != null && memories.value != null) {
                memories.value!!.forEach { memory ->
                    ShowMemoryCard(memory)
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp)),
                    colors = ButtonDefaults.buttonColors(backgroundColor = LightBlue1),
                    onClick = {
                        navController.navigate(BottomBarScreen.CreateMemoryScreen.route)
                    }
                ) {
                    Icon(
                        Icons.Outlined.Add,
                        tint = MaterialTheme.colors.secondary,
                        contentDescription = "Add icon"
                    )
                    Text(
                        text = "Create new memory",
                        color = MaterialTheme.colors.secondary,
                        style = MaterialTheme.typography.body1
                    )
                }
            }

        }

    }
}

@Composable
fun ShowMemoryCard(memory: Memory) {
    val context = LocalContext.current
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
                    Image(
                        bitmap = BitmapFactory.decodeFile(memory.imageUri).asImageBitmap(),
                        contentDescription = "strawberries",
                        modifier = Modifier
                            .fillMaxWidth(0.4F)
                            .clip(RoundedCornerShape(10.dp))
                    )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 10.dp)
                ) {
                    //Icon(painter = painterResource(id = R.drawable.c))
                    Row(Modifier.fillMaxWidth()) {
                        Image(painter = painterResource(id = R.drawable.ic_explore), contentDescription = null,Modifier
                            .size(25.dp,25.dp)
                            .padding(top=2.dp,end=4.dp))
                        Text(text = "${memory.location} ",modifier = Modifier.padding(bottom=3.dp))
                    }
                    Spacer(modifier = Modifier.height(5.dp))
                    Row(Modifier.fillMaxWidth()) {
                        Image(painterResource(R.drawable.ic_calendar),contentDescription= null,Modifier
                            .size(25.dp,25.dp)
                            .padding(bottom = 4.dp, end = 3.dp))
                        Text(text = " ${memory.date} ",modifier = Modifier.padding(top=3.dp))
                    }
                    Spacer(modifier = Modifier.height(5.dp))
                    Row(Modifier.fillMaxWidth()) {
                        Image(painter = painterResource(id = R.drawable.ic_light), contentDescription = null,Modifier
                            .size(25.dp,25.dp)
                            .padding(bottom = 3.dp))
                        Text(text = " ${memory.light} ",modifier = Modifier.padding(top=3.dp))
                    }
                }
            }
        }

    }
}
