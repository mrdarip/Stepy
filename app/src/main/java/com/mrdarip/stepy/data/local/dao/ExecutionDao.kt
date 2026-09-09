package com.mrdarip.stepy.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RewriteQueriesToDropUnusedColumns
import com.mrdarip.stepy.data.local.entities.ExecutionEntity

@Dao
interface ExecutionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExecution(execution: ExecutionEntity): Long

    /**
     * Retrieves the last [count] executions for each of the given steps.
     *
     * @param stepsIds The IDs of the steps whose executions are to be retrieved.
     * @param count The maximum number of executions to retrieve for each step.
     * @return A list of [ExecutionEntity] representing the last [count] executions of the given steps.
     */
    @RewriteQueriesToDropUnusedColumns
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