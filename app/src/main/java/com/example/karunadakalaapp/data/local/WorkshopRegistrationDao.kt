package com.example.karunadakalaapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkshopRegistrationDao {
    @Query("SELECT * FROM workshop_registrations ORDER BY id DESC")
    fun observeAll(): Flow<List<WorkshopRegistrationEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(registration: WorkshopRegistrationEntity)
}
