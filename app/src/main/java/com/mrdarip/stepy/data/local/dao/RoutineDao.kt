package com.mrdarip.stepy.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mrdarip.stepy.data.local.entities.RoutineEntity

@Dao
interface RoutineDao {
    @Query("SELECT * FROM routines")
    suspend fun getAllRoutines(): List<RoutineEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRoutine(routine: RoutineEntity)
}