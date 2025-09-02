package com.mrdarip.stepy.data.repository

import com.mrdarip.stepy.data.local.dao.TaskDao
import com.mrdarip.stepy.data.mapper.toDomain
import com.mrdarip.stepy.data.mapper.toEntity
import com.mrdarip.stepy.domain.model.Task
import com.mrdarip.stepy.domain.repository.TaskRepository

class TaskRepositoryImpl(
    private val taskDao: TaskDao
) : TaskRepository {

    override suspend fun getTasks(): List<Task> {
        return taskDao.getAllTasks().map { it.toDomain() }
    }

    override suspend fun addTask(task: Task) {
        taskDao.insertTask(task.toEntity())
    }

    override suspend fun getTask(id: Int): Task {
        return taskDao.getTaskById(id).toDomain()
    }
}