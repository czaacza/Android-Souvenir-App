package fi.metropolia.project.souvenirapp.model.data

import androidx.room.*

@Dao
interface MemoryDao {

    @Query("SELECT * FROM memory")
    fun getAll(): List<Memory>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(memory: Memory): Long

    @Delete
    suspend fun delete(memory: Memory)

    @Update
    suspend fun update(memory: Memory)
}