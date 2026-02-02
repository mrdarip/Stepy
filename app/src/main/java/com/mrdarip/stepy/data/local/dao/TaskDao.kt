package com.mrdarip.stepy.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.mrdarip.stepy.data.local.entities.StepEntity
import com.mrdarip.stepy.data.local.entities.TaskEntity

@Dao
interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertTask(step: TaskEntity)

    @Query("SELECT * FROM tasks")
    suspend fun getAllTasks(): List<TaskEntity>

    @Query("SELECT * FROM tasks WHERE id = :id")
    suspend fun getTaskById(id: Int): TaskEntity

    /**
     * Retrieves the active steps of a task from the database.
     *
     * @param taskId The ID of the task whose steps are to be retrieved.
     * @return A list of [StepEntity] representing the active steps of the task.
     */
    @Query("SELECT * FROM steps WHERE taskId = :taskId AND position IS NOT NULL ORDER BY position ASC")
    suspend fun getStepsOfTask(taskId: Int): List<StepEntity>

    @Query(
        """
        SELECT
          avg(e.`end` - e.start) 
        FROM 
          steps as s 
          INNER JOIN executions as e ON s.id = e.stepId 
        WHERE 
          s.taskId = :taskId 
          and s.position IS NOT NULL 
        GROUP BY 
          s.id 
        ORDER BY 
          s.position ASC
    """
    )
    suspend fun getStepsStatsOfTask(taskId: Int): List<Int>

    @Upsert
    suspend fun upsertTask(task: TaskEntity)
}