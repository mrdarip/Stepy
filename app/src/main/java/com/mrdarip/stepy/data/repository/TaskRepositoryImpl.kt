package com.mrdarip.stepy.data.repository

import com.mrdarip.stepy.data.local.dao.ExecutionDao
import com.mrdarip.stepy.data.local.dao.TaskDao
import com.mrdarip.stepy.data.mapper.toDomain
import com.mrdarip.stepy.data.mapper.toEntity
import com.mrdarip.stepy.domain.model.Execution
import com.mrdarip.stepy.domain.model.Step
import com.mrdarip.stepy.domain.model.StepStats
import com.mrdarip.stepy.domain.model.StepWithStats
import com.mrdarip.stepy.domain.model.Task
import com.mrdarip.stepy.domain.repository.TaskRepository

class TaskRepositoryImpl(
    private val taskDao: TaskDao,
    private val executionDao: ExecutionDao
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

    override suspend fun getStepsOfTask(taskId: Int): List<Step> {
        return taskDao.getStepsOfTask(taskId).map { it.toDomain() }
    }

    override suspend fun upsertTask(task: Task) {
        taskDao.upsertTask(task.toEntity())
    }

    override suspend fun getExecutionsOfTask(taskId: Int): List<Execution> {
        return taskDao.getExecutionsOfTask(taskId)
    }


    override suspend fun getStepsAndStatsOfTask(
        taskId: Int,
        maxSampleSize: Int
    ): List<StepWithStats> {
        // use daos to get all steps of that task, then get 20 executions, and use them to calculate the stats
        val steps = taskDao.getStepsOfTask(taskId)
        val stats = executionDao.getExecutionsOfSteps(steps.map { it.id }, maxSampleSize)

        val stepWithStats = mutableListOf<StepWithStats>()
        for (step in steps) {
            val statsForStep = stats.filter { it.stepId == step.id }
            val statsForStepDomain = statsForStep.map { it.toDomain() }

            stepWithStats.add(
                StepWithStats(
                    step.toDomain(),
                    StepStats.fromExecutions(statsForStepDomain)
                )
            )
        }

        return stepWithStats
    }
}