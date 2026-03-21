package com.mrdarip.stepy.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.mrdarip.stepy.data.local.entities.StepEntity
import com.mrdarip.stepy.data.local.entities.TaskEntity
import com.mrdarip.stepy.domain.model.Execution

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
        SELECT * FROM executions
        WHERE stepId IN (
            SELECT id FROM steps
            WHERE taskId = :taskId
            AND position IS NOT NULL
        )
    """
    )
    suspend fun getExecutionsOfTask(taskId: Int): List<Execution>

    @Upsert
    suspend fun upsertTask(task: TaskEntity)
}