package fi.metropolia.project.souvenirapp.view.screens

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fi.metropolia.project.souvenirapp.model.data.Memory
import fi.metropolia.project.souvenirapp.model.getBitmapFromSampleFile
import fi.metropolia.project.souvenirapp.viewmodel.MemoryDatabaseViewModel
import java.io.IOException


@Composable
fun ListScreen(
    memoryDatabaseViewModel: MemoryDatabaseViewModel
) {
    val memories = memoryDatabaseViewModel.memories.observeAsState()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopAppBar(
            modifier = Modifier
                .fillMaxWidth(),
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

        if (memories != null && memories.value != null) {
            Column(Modifier.verticalScroll(rememberScrollState())) {
                memories.value!!.forEach { memory ->
                    ShowMemoryCard(memory)
                }
            }
        }
    }
}

fun Context.assetsToBitmap(fileName: String): Bitmap? {
    return try {
        with(assets.open(fileName)) {
            BitmapFactory.decodeStream(this)
        }
    } catch (e: IOException) {
        null
    }
}

@Composable
fun ShowMemoryCard(memory: Memory) {
    val context = LocalContext.current
    val bitmap: Bitmap? = getBitmapFromSampleFile()
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
                if (bitmap != null) {
                    Image(
                        bitmap = BitmapFactory.decodeFile(memory.imageUri).asImageBitmap(),
                        contentDescription = "strawberries",
                        /*contentScale = ContentScale.Crop,*/
                        modifier = Modifier
                            .fillMaxWidth(0.4F)
                            .clip(RoundedCornerShape(10.dp))
                        /*.border(
                            3.dp,
                            Color(0xFF000000),
                            RoundedCornerShape(10.dp)
                        )*/
                    )
                }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 10.dp)
                ) {
                    //Icon(painter = painterResource(id = R.drawable.c))
                    Row(Modifier.fillMaxWidth()) {
                        Icon(Icons.Outlined.LocationOn, contentDescription = null)
                        Text(text = "${memory.location} ")
                    }
                    Spacer(modifier = Modifier.height(5.dp))
                    Row(Modifier.fillMaxWidth()) {
                        Icon(Icons.Outlined.Face, contentDescription = null)
                        Text(text = " DATE ")
                    }
                    Spacer(modifier = Modifier.height(5.dp))
                    Row(Modifier.fillMaxWidth()) {
                        Icon(Icons.Outlined.MoreVert, contentDescription = null)
                        Text(text = " LIGHT ")
                    }
                }
            }
            /*Icon(
                painter = painterResource(id= R.drawable.ic_calendar),
                contentDescription = null
            )*/
        }

    }
}
