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
        return Execution(
            parentExecutionId = parentExecution?.id,
            stepId = step.id,
            position = step.position,
            start = System.currentTimeMillis() / 1000L,
            end = System.currentTimeMillis() / 1000L,
            parentRoutineId = routine?.id
        )
    }
}