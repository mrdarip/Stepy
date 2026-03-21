package com.mrdarip.stepy.domain.repository

import com.mrdarip.stepy.domain.model.Execution
import com.mrdarip.stepy.domain.model.Step
import com.mrdarip.stepy.domain.model.StepWithStats
import com.mrdarip.stepy.domain.model.Task

interface TaskRepository {
    suspend fun getTasks(): List<Task>
    suspend fun addTask(task: Task)
    suspend fun getTask(id: Int): Task
    suspend fun getStepsOfTask(taskId: Int): List<Step>
    suspend fun upsertTask(task: Task)

    suspend fun getExecutionsOfTask(taskId: Int): List<Execution>
    suspend fun getStepsAndStatsOfTask(taskId: Int, maxSampleSize: Int): List<StepWithStats>
}