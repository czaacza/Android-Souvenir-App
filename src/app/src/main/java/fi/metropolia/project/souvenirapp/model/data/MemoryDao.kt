package fi.metropolia.project.souvenirapp.model.data

import androidx.room.*

@Dao
interface MemoryDao {
    @Query("SELECT * FROM MemoryEntity")
    fun getAll(): List<MemoryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(memory: MemoryEntity): Long

    @Delete
    suspend fun delete(memory: MemoryEntity)

    @Update
    suspend fun update(memory: MemoryEntity)

    @Query("DELETE FROM MemoryEntity")
    suspend fun clearTable()
}