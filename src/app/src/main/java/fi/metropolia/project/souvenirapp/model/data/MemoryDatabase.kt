package fi.metropolia.project.souvenirapp.model.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [MemoryEntity::class], version = 1)
abstract class MemoryDatabase : RoomDatabase() {
    abstract fun memoryDao(): MemoryDao

    companion object {
        private var instance: MemoryDatabase? = null

        @Synchronized
        fun get(context: Context): MemoryDatabase {
            if (instance == null) {
                instance =
                    Room.databaseBuilder(
                        context.applicationContext,
                        MemoryDatabase::class.java, "memory.db"
                    ).build()
            }
            return instance!!
        }
    }
}