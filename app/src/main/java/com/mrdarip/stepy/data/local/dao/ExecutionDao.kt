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
        WITH ranked AS (
            SELECT *,
                   ROW_NUMBER() OVER (
                       PARTITION BY stepId
                       ORDER BY start DESC
                   ) AS rn
            FROM executions
            
            where stepId in (:stepsIds)
        )
        SELECT *
        FROM ranked
        WHERE rn <= :count;
        """
    )
    suspend fun getExecutionsOfSteps(stepsIds: List<Long>, count: Int): List<ExecutionEntity>

}