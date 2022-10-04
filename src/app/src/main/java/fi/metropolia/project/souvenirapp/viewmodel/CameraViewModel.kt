package fi.metropolia.project.souvenirapp.viewmodel

import android.app.Application
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.core.content.FileProvider
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import java.io.File

class CameraViewModel(application: Application) : AndroidViewModel(application) {
    private val app = application
    val imageBitmap = MutableLiveData<Bitmap>()

    private val isLaunched = MutableLiveData(false)

    private val imageFile: File
    private val imageUri: Uri
    private val imageAbsolutePath: String

    init {
        imageFile = createFileForImage("createScreenImage")
        imageUri = getImgUri(imageFile)
        imageAbsolutePath = imageFile.absolutePath
    }

    @Composable
    fun SetLauncher() {
        val isLaunchedState = isLaunched.observeAsState()
        val launcher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) {
            if (it) {
                imageBitmap.value = BitmapFactory.decodeFile(imageAbsolutePath)
            } else {
                Log.d("ERROR", "Sorry, I was not able to take a picture.")
            }
        }
        if (isLaunchedState.value == true) {
            launcher.launch(imageUri)
            isLaunched.value = false
        }
    }

    fun launch() {
        isLaunched.value = true;
    }

    private fun createFileForImage(fileName: String): File {
        val filePath = app.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(fileName, ".jpg", filePath)
    }

    private fun getImgUri(imgFile: File): Uri {
        val photoURI: Uri =
            FileProvider.getUriForFile(
                app, "fi.metropolia.project.souvenirapp.fileprovider", imgFile
            )
        return photoURI
    }
}