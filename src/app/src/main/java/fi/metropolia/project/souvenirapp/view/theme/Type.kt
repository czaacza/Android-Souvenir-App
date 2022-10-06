package fi.metropolia.project.souvenirapp.view.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import fi.metropolia.project.souvenirapp.R


val Rubik = FontFamily(
    Font(R.font.rubik_regular)
)

// Set of Material typography styles to start with
val Typography = Typography(
    body1 = TextStyle(
        fontFamily = Rubik,
        fontWeight = FontWeight(400),
        fontSize = 16.sp
    ),

    h1 = TextStyle(
        fontFamily = Rubik,
        fontWeight = FontWeight(700),
        fontSize = 20.sp
    ),

    subtitle1 = TextStyle(
        fontFamily = Rubik,
        fontWeight = FontWeight(400),
        fontSize = 12.sp
    ),

    subtitle2 = TextStyle(
        fontFamily = Rubik,
        fontWeight = FontWeight(400),
        fontSize = 14.sp
    ),



    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)