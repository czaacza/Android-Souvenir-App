package fi.metropolia.project.souvenirapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import fi.metropolia.project.souvenirapp.ui.MainScreen
import fi.metropolia.project.souvenirapp.ui.theme.SouvenirAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SouvenirAppTheme {
                MainScreen()
            }
        }
    }
}