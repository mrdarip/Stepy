package com.mrdarip.stepy.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mrdarip.stepy.data.local.entities.ExecutionEntity

@Dao
interface ExecutionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExecution(execution: ExecutionEntity): Long

    @Query(
        """
            SELECT * FROM executions
            WHERE stepId IN (:stepsIds)
            ORDER BY start DESC -- most recent first
            LIMIT :count
        """
    )
    fun getExecutionsOfSteps(stepsIds: List<Long>, count: Int): List<ExecutionEntity>

}