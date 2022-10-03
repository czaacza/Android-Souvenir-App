package fi.metropolia.project.souvenirapp.view.screens

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fi.metropolia.project.souvenirapp.model.getBitmapFromSampleFile
import java.io.IOException


@Composable
fun ListScreen(
) {
    val context = LocalContext.current
    val bitmap: Bitmap? = getBitmapFromSampleFile()
    Card(
        modifier = Modifier
            .fillMaxWidth()
            /*.fillMaxHeight(0.22F)*/
            .padding(15.dp),
        elevation = 20.dp
    ) {
        Column(modifier = Modifier.padding(5.dp)) {
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
            Row() {
                if(bitmap != null) {
                    Image(
                        bitmap = bitmap.asImageBitmap(),
                        contentDescription = "strawberries",
                        /*contentScale = ContentScale.Crop,*/
                        modifier = Modifier
                            .padding(start = 10.dp, bottom = 10.dp)
                            .fillMaxWidth(0.3F)
                            .clip(RoundedCornerShape(10.dp))
                        /*.border(
                            3.dp,
                            Color(0xFF000000),
                            RoundedCornerShape(10.dp)
                        )*/
                    )
                }
                Text(
                    text = "DESCRIPTION", modifier = Modifier
                        .align(CenterVertically)
                        .padding(start = 10.dp, end = 5.dp, bottom = 10.dp)
                )
            }
            /*Icon(
                painter = painterResource(id= R.drawable.ic_calendar),
                contentDescription = null
            )*/
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                //Icon(painter = painterResource(id = R.drawable.c))
                Row(Modifier.fillMaxWidth(0.3F)) {
                    Icon(Icons.Outlined.LocationOn, contentDescription = null)
                    Text(text = " HERE ")
                }
                Row(Modifier.fillMaxWidth(0.4F)) {
                    Icon(Icons.Outlined.Face, contentDescription = null)
                    Text(text = " DATE ")
                }
                Row(Modifier.fillMaxWidth(0.5F)) {
                    Icon(Icons.Outlined.MoreVert, contentDescription = null)
                    Text(text = " LIGHT ")
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
