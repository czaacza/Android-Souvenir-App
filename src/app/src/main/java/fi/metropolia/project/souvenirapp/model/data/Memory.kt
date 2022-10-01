package fi.metropolia.project.souvenirapp.model.data

import android.graphics.Bitmap
import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Memory(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "latitude") val latitude: Double,
    @ColumnInfo(name = "longitude") val longitude: Double,
    @ColumnInfo(name = "imageUri") val imageUri: String,
) {
    override fun toString(): String {
        return "id: ${id}\n" +
                "title: ${title}\n" +
                "description: ${description}\n" +
                "location: ($latitude $longitude)\n" +
                "image: $imageUri"
    }
}