package fi.metropolia.project.souvenirapp.model.data

import android.graphics.Bitmap
import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Memory(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "location") val location: String,
    @ColumnInfo(name = "imageUri") val imageUri: String,
) {
    override fun toString(): String {
        return "id: ${id}\n" +
                "title: ${title}\n" +
                "description: ${description}\n" +
                "location: ($location)\n" +
                "image: $imageUri"
    }
}