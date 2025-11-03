package com.mrdarip.stepy.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.mrdarip.stepy.data.local.entities.StepEntity

@Dao
interface StepDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStep(step: StepEntity)

    @Query("DELETE FROM steps WHERE id = :stepId")
    suspend fun deleteStepById(stepId: Int)

    @Upsert
    suspend fun upsertStep(step: StepEntity)

    @Upsert
    suspend fun upsertSteps(steps: List<StepEntity>)

    @Query("UPDATE steps SET position = -position-1 WHERE taskId = :taskId")
    suspend fun negateStepsOf(taskId: Long)

    @Query("SELECT * FROM steps WHERE taskId = :id")
    suspend fun getStepsOfTask(id: Long): List<StepEntity>

    @Query("UPDATE steps SET position = NULL WHERE id IN (:map)")
    suspend fun setStepsAsUnused(map: List<Long>)

}