package com.mrdarip.stepy.domain.repository

import com.mrdarip.stepy.domain.model.Task

interface TaskRepository {
    suspend fun getTasks(): List<Task>
    suspend fun addTask(task: Task)
    suspend fun getTask(id: Int): Task

}