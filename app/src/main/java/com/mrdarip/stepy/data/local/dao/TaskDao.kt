package com.mrdarip.stepy.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mrdarip.stepy.data.local.entities.TaskEntity

@Dao
interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(step: TaskEntity)

    @Query("SELECT * FROM tasks")
    suspend fun getAllTasks(): List<TaskEntity>

    @Query("SELECT * FROM tasks WHERE id = :id")
    suspend fun getTaskById(id: Int): TaskEntity

}