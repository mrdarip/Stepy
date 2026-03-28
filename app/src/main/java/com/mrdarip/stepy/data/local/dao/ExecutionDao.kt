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

    /**
     * Retrieves up to `count` most-recent executions for each step ID in `stepsIds`.
     *
     * For each provided `stepId`, returns at most `count` executions ordered by `start` descending (most recent first).
     * The overall ordering of rows across different `stepId` values is not guaranteed.
     *
     * @param stepsIds The list of step IDs to fetch executions for.
     * @param count The maximum number of most-recent executions to return per step ID.
     * @return A list of ExecutionEntity containing at most `count` entries per step ID.
     */
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