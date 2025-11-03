package com.mrdarip.stepy.data.repository

import com.mrdarip.stepy.data.local.dao.ExecutionDao
import com.mrdarip.stepy.data.mapper.toEntity
import com.mrdarip.stepy.domain.model.Execution
import com.mrdarip.stepy.domain.model.Routine
import com.mrdarip.stepy.domain.model.Step
import com.mrdarip.stepy.domain.repository.ExecutionRepository

class ExecutionRepositoryImpl(
    private val executionDao: ExecutionDao
) : ExecutionRepository {
    override suspend fun addExecution(execution: Execution): Long {
        return executionDao.insertExecution(execution.toEntity())
    }

    override fun createExecutionNow(
        step: Step,
        parentExecution: Execution?,
        routine: Routine?
    ): Execution {
        if (step.position == null) {
            throw IllegalArgumentException("Step position cannot be null, you are trying to create an execution for an unused step!")
        }

        return Execution(
            parentExecutionId = parentExecution?.parentExecutionId ?: parentExecution?.id,
            stepId = step.id,
            position = step.position,
            start = System.currentTimeMillis() / 1000L,
            end = System.currentTimeMillis() / 1000L,
            parentRoutineId = routine?.id
        )
    }
}